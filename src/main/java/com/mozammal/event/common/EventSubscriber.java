package com.mozammal.event.common;

import java.lang.reflect.ParameterizedType;

public abstract class EventSubscriber<E extends Event> {
  public abstract void process(E event);

  public Class<E> getEventType() {
    return (Class<E>)
        ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
  }
}
