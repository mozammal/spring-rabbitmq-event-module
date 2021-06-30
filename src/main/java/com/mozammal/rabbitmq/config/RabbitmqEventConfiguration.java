package com.mozammal.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mozammal.event.common.EventSubscriber;
import com.mozammal.event.common.EventUtils;
import com.mozammal.event.common.config.CommonProperties;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.mozammal.rabbitmq.config.RabbitmqProperties.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RabbitmqEventConfiguration {
  private final CommonProperties commonProperties;

  @Bean
  public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  public RabbitTemplate eventRabbitTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jacksonMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public BeanPostProcessor eventSubscriberBeanPostProcessor(
      ConnectionFactory connectionFactory, AmqpAdmin admin) {
    BeanPostProcessor beanPostProcessor =
        new BeanPostProcessor() {
          @Override
          public Object postProcessBeforeInitialization(Object bean, String beanName)
              throws BeansException {
            return bean;
          }

          @Override
          public Object postProcessAfterInitialization(Object bean, String beanName)
              throws BeansException {
            if (bean instanceof EventSubscriber) {
              EventSubscriber eventSubscriber = (EventSubscriber) bean;
              Queue subscriberQueue = getSubscriberQueue(eventSubscriber);
              admin.declareQueue(subscriberQueue);
              Binding subscriberBinding = getSubscriberBinding(eventSubscriber, subscriberQueue);
              admin.declareBinding(subscriberBinding);
              AbstractMessageListenerContainer subscriberMessageListenerContainer =
                  getSubscriberMessageListenerContainer(
                      eventSubscriber, connectionFactory, subscriberQueue);
              subscriberMessageListenerContainer.start();
              log.debug("Found event subscriber {}", bean.getClass().getSimpleName());
            }

            return bean;
          }
        };
    return beanPostProcessor;
  }

  @Bean
  public TopicExchange eventTopicExchange() {
    return new TopicExchange(
        EventUtils.getEventTopicName(commonProperties.getEnvironment()), true, false);
  }

  @Bean
  public MessageConverter jacksonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  private Binding getSubscriberBinding(EventSubscriber eventSubscriber, Queue subscriberQueue) {
    return BindingBuilder.bind(subscriberQueue)
        .to(eventTopicExchange())
        .with(EventUtils.getSubscribedEventTypeName(eventSubscriber));
  }

  private AbstractMessageListenerContainer getSubscriberMessageListenerContainer(
      EventSubscriber eventSubscriber, ConnectionFactory connectionFactory, Queue subscriberQueue) {
    SimpleMessageListenerContainer messageListenerContainer =
        new SimpleMessageListenerContainer(connectionFactory);
    messageListenerContainer.setQueueNames(subscriberQueue.getName());
    MessageListenerAdapter messageListenerAdapter =
        new MessageListenerAdapter(eventSubscriber, "process");
    messageListenerAdapter.setMessageConverter(jacksonMessageConverter());
    messageListenerContainer.setMessageListener(messageListenerAdapter);
    return messageListenerContainer;
  }

  private Queue getSubscriberQueue(EventSubscriber eventSubscriber) {
    return new Queue(
        EventUtils.getSubscribedQueueName(eventSubscriber, commonProperties.getEnvironment()),
        true,
        false,
        false,
        deadLetterQueueAndExchangeConfig());
  }

  @Bean
  public Queue deadLetterQueue() {
    return new Queue(DEAD_LETTER_QUEUE, true);
  }

  @Bean
  public FanoutExchange deadLetterExchange() {
    return new FanoutExchange(DEAD_LETTER_EXCHANGE);
  }

  @Bean
  public Binding deadLetterBinding() {
    return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
  }

  public Map<String, Object> deadLetterQueueAndExchangeConfig() {
    Map<String, Object> configMap = new HashMap<>();
    configMap.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
    configMap.put("x-message-ttl", 5000);
    return configMap;
  }
}
