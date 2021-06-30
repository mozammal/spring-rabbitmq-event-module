package com.mozammal.event.common.type;

import com.mozammal.event.common.Event;
import lombok.Data;

/**
 * Sample payload
 *
 * <p>{ "userId": 100"}
 */
@Data
public class PaidUserSubscriptionEvent implements Event {
  private Integer userId;
}
