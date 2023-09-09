package com.safetynet.alerts.web.configuration;

import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Some javadoc.
 * 
 * Configuration class for customizing the HTTP trace actuator feature.
 * Provides a bean for creating an {@link InMemoryHttpExchangeRepository}
 * instance to store and manage HTTP exchange information.
 */
@Configuration
public class HttpTraceActuatorConfiguration {

  /**
   * Creates an {@link InMemoryHttpExchangeRepository} bean instance.
   *
   * @return An {@link InMemoryHttpExchangeRepository} instance.
   */
  @Bean
  public HttpExchangeRepository httpTraceRepository() {
    return new InMemoryHttpExchangeRepository();
  }
}