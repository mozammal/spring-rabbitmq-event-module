package com.mozammal.event.common.type;

import com.mozammal.event.common.Event;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ExceptionThrower implements Event {
  private Integer userId;
}
