package com.hyk.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

  @Bean
  public ConnectionMaker connectionMaker() {
    return new DConnectionMaker();
  }

}
