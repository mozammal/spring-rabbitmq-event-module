package com.mozammal.web;

import com.mozammal.event.common.EventPublisher;
import com.mozammal.event.common.type.ExceptionThrower;
import com.mozammal.event.common.type.PaidUserSubscriptionEvent;
import com.mozammal.event.common.type.WelcomeEmailEvent;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MainController {

  private final EventPublisher eventPublisher;

  @PostMapping("/events/welcome-email/")
  public void sendUserWelcomeEmail(@RequestBody WelcomeEmailEvent welcomeEmailEvent) {
    eventPublisher.send(welcomeEmailEvent);
  }

  @PostMapping("/events/paid-subscription/")
  public void senPaidUserSubscriptionEvent(
      @RequestBody PaidUserSubscriptionEvent paidUserSubscriptionEvent) {
    eventPublisher.send(paidUserSubscriptionEvent);
  }

  @PostMapping("/events/exception/")
  public void throwError(@RequestBody ExceptionThrower exceptionThrower) {
    eventPublisher.send(exceptionThrower);
  }
}
