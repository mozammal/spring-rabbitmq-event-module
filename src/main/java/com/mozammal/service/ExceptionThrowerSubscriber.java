package com.mozammal.service;

import com.mozammal.event.common.EventSubscriber;
import com.mozammal.event.common.type.ExceptionThrower;
import com.mozammal.event.common.type.WelcomeEmailEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExceptionThrowerSubscriber extends EventSubscriber<ExceptionThrower> {

  @Override
  public void process(ExceptionThrower event) {
    String s = null;
    try {
      int len = s.length();
    } catch (Exception e) {
      throw new AmqpRejectAndDontRequeueException(e.getMessage());
    }
  }
}
