package com.employee.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeConfiguration {
  @Bean
  public ModelMapper modelMapper() {

    return new ModelMapper();
  }

//  @Bean
//  public RestTemplate restTemplate() {
//
//    return new RestTemplate();
//  }
}
