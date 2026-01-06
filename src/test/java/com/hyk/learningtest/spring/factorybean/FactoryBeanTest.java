package com.hyk.learningtest.spring.factorybean;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration // 설정파일 이름을 지정하지 않으면 클래스이름 + "-context.xml"이 디폴트로 사용됨
public class FactoryBeanTest {

  @Autowired
  private ApplicationContext context;

  @Test
  public void getMessageFromFactoryBean() {
    Object message = context.getBean("message");
    assertThat(message, instanceOf(Message.class));
    assertThat(((Message) message).getText(), is("Factory Bean"));
  }

  @Test
  public void getFactoryBean() {
    Object factory = context.getBean("&message");
    assertThat(factory, instanceOf(MessageFactoryBean.class));
  }

}
