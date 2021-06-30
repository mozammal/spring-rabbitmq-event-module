package com.mozammal.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.mozammal.event.common.Event;
import com.mozammal.event.common.EventPublisher;
import com.mozammal.event.common.EventUtils;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqEventPublisher implements EventPublisher {
  private final RabbitTemplate rabbitTemplate;
  private final TopicExchange topicExchange;

  @Override
  public void send(Event event) {
    try {
      String eventName = EventUtils.getEventName(event);
      rabbitTemplate.convertAndSend(topicExchange.getName(), EventUtils.getEventName(event), event);
      log.info("Sent {} to {} with key {}", event, topicExchange.getName(), eventName);
    } catch (Exception e) {
      log.error("Could not send even {} due to {}", event, e.getCause());
    }
  }
}
