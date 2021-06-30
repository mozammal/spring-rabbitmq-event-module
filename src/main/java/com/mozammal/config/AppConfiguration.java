package com.mozammal.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mozammal.event.common.config.CommonProperties;

@Configuration
public class AppConfiguration {
  @Bean
  @ConfigurationProperties("mozammal.common")
  public CommonProperties commonProperties() {
    return new CommonProperties();
  }
}
