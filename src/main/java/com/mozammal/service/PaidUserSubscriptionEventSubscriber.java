package com.mozammal.service;

import com.mozammal.event.common.EventSubscriber;
import com.mozammal.event.common.type.PaidUserSubscriptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaidUserSubscriptionEventSubscriber
    extends EventSubscriber<PaidUserSubscriptionEvent> {

  @Override
  public void process(PaidUserSubscriptionEvent event) {
    log.info("Received {} event {} for processing", event.getClass(), event);
  }
}
