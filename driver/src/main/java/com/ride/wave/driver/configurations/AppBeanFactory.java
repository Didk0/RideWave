package com.ride.wave.driver.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeanFactory {

  @Bean
  public ModelMapper modelMapper() {

    return new ModelMapper();
  }
}
