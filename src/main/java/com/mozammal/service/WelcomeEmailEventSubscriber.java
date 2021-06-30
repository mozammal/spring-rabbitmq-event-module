package com.mozammal.service;

import com.mozammal.event.common.EventSubscriber;
import com.mozammal.event.common.type.WelcomeEmailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WelcomeEmailEventSubscriber extends EventSubscriber<WelcomeEmailEvent> {
  @Override
  public void process(WelcomeEmailEvent event) {
    try {
      log.info("Received {} event {} for processing", event.getClass(), event);
    } catch (Exception e) {
      log.error("found error in email sending: {}", e.getMessage());
      throw new AmqpRejectAndDontRequeueException(e.getMessage());
    }
  }
}
