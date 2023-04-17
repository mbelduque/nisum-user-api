package com.nisum.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }
}
