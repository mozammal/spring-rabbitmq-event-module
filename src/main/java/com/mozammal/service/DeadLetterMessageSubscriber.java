package com.mozammal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.mozammal.rabbitmq.config.RabbitmqProperties.DEAD_LETTER_QUEUE;

@Slf4j
@RabbitListener(queues = DEAD_LETTER_QUEUE)
@Service
public class DeadLetterMessageSubscriber {

  @RabbitHandler
  public void handleError(Object message) {
    log.error("received error message from dead queue!: {}", message);
  }
}
