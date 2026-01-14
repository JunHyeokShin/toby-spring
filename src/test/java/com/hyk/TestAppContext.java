package com.hyk;

import com.hyk.user.service.DummyMailSender;
import com.hyk.user.service.UserService;
import com.hyk.user.service.UserServiceTest.TestUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

@Configuration
public class TestAppContext {

  @Bean
  public UserService testUserService() {
    return new TestUserService();
  }

  @Bean
  public MailSender mailSender() {
    return new DummyMailSender();
  }

}
