package com.nisum.api.config;

import com.nisum.api.util.Util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {

  @Bean
  public Util util(){
    return new Util();
  }
}
