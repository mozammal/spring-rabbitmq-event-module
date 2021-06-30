package com.mozammal.event.common;

import com.mozammal.event.common.config.Environment;
import static com.mozammal.event.common.config.EventConstants.*;

public class EventUtils {
  public static String getEventTopicName(Environment environment) {
    return EVENT_TOPIC_NAME_PREFIX + "-" + environment.name().toLowerCase();
  }

  public static String getEventName(Event event) {
    return event.getClass().getSimpleName();
  }

  public static String getSubscriberName(EventSubscriber eventSubscriber) {
    return eventSubscriber.getClass().getSimpleName();
  }

  public static String getSubscribedQueueName(
      EventSubscriber eventSubscriber, Environment environment) {
    return EVENT_QUEUE_NAME_SUFFIX
        + getSubscriberName(eventSubscriber)
        + "-"
        + environment.name().toLowerCase();
  }

  public static String getSubscribedEventTypeName(EventSubscriber eventSubscriber) {
    return eventSubscriber.getEventType().getSimpleName();
  }

  private EventUtils() {}
}
