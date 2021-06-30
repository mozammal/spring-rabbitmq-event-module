package com.mozammal.event.common.type;

import com.mozammal.event.common.Event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Sample payload
 *
 * <p>{ "userId": 100, "firstname": "mozammal", "lastname": "hossain", "email":"mozammal@gmail.com"}
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WelcomeEmailEvent implements Event {
  private Integer userId;

  private String firstname;

  private String lastname;

  private String email;
}
