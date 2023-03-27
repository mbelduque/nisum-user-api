package com.nisum.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Clase de configuración para encriptar contraseñas con BCryptPasswordEncoder.
 */
@Configuration
public class EncoderConfig {
  /**
   * Bean para encriptar contraseñas.
   *
   * @return bean de BCryptPasswordEncoder.
   */
  @Bean
  public org.springframework.security.crypto.password.PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
