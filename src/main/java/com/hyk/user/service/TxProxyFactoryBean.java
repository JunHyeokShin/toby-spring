package com.hyk.user.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

public class TxProxyFactoryBean implements FactoryBean<Object> {

  private Object target;
  private PlatformTransactionManager transactionManager;
  String pattern;
  Class<?> serviceInterface;

  public void setTarget(Object target) {
    this.target = target;
  }

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setServiceInterface(Class<?> serviceInterface) {
    this.serviceInterface = serviceInterface;
  }

  @Nullable
  @Override
  public Object getObject() {
    TransactionHandler txHandler = new TransactionHandler();
    txHandler.setTarget(target);
    txHandler.setTransactionManager(transactionManager);
    txHandler.setPattern(pattern);
    return Proxy.newProxyInstance(
        getClass().getClassLoader(),
        new Class[]{serviceInterface},
        txHandler
    );
  }

  @Nullable
  @Override
  public Class<?> getObjectType() {
    return serviceInterface;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }

}
