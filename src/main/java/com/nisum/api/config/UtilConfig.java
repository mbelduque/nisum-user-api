package com.nisum.api.config;

import com.nisum.api.util.Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para instancias de Util.
 */
@Configuration
public class UtilConfig {
  /**
   * Bean para instanciar la clase Util
   *
   * @return bean de Util.
   */
  @Bean
  public Util util() {
    return new Util();
  }
}
