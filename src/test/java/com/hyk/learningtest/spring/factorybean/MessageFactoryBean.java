package com.hyk.learningtest.spring.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

public class MessageFactoryBean implements FactoryBean<Message> {

  private String text;

  public void setText(String text) {
    this.text = text;
  }

  @Nullable
  @Override
  public Message getObject() {
    return Message.newMessage(this.text);
  }

  @Nullable
  @Override
  public Class<?> getObjectType() {
    return Message.class;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

}
