package com.hyk.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

    dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
    dataSource.setUrl("jdbc:mysql://localhost:3306/springbook");
    dataSource.setUsername("root");
    dataSource.setPassword("password1234");

    return dataSource;
  }

  @Bean
  public UserDao userDao() {
    UserDao userDao = new UserDao();
    userDao.setDataSource(dataSource());
    return userDao;
  }

}
