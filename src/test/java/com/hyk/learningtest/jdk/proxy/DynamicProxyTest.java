package com.hyk.learningtest.jdk.proxy;

import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DynamicProxyTest {

  @Test
  public void simpleProxy() {
    Hello hello = new HelloTarget();
    assertThat(hello.sayHello("Toby"), is("Hello Toby"));
    assertThat(hello.sayHi("Toby"), is("Hi Toby"));
    assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));

    Hello proxiedHello = (Hello) Proxy.newProxyInstance(
        getClass().getClassLoader(),
        new Class[]{Hello.class},
        new UppercaseHandler(new HelloTarget())
    );
    assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
    assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
    assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
  }

  @Test
  public void proxyFactoryBean() {
    ProxyFactoryBean pfBean = new ProxyFactoryBean();
    pfBean.setTarget(new HelloTarget());
    pfBean.addAdvice(new UppercaseAdvice());

    Hello proxiedHello = (Hello) pfBean.getObject();
    assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
    assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
    assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
  }

  @Test
  public void pointcutAdvisor() {
    ProxyFactoryBean pfBean = new ProxyFactoryBean();
    pfBean.setTarget(new HelloTarget());

    NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
    pointcut.setMappedName("sayH*");

    pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));

    Hello proxiedHello = (Hello) pfBean.getObject();
    assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
    assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
    assertThat(proxiedHello.sayThankYou("Toby"), is("Thank You Toby"));
  }

  @Test
  public void classNamePointcutAdvisor() {
    NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut() {
      @Override
      public ClassFilter getClassFilter() {
        return new ClassFilter() {
          @Override
          public boolean matches(Class<?> clazz) {
            return clazz.getSimpleName().startsWith("HelloT");
          }
        };
      }
    };
    classMethodPointcut.setMappedName("sayH*");

    class HelloWorld extends HelloTarget {

    }

    class HelloToby extends HelloTarget {

    }

    checkAdviced(new HelloTarget(), classMethodPointcut, true);
    checkAdviced(new HelloWorld(), classMethodPointcut, false);
    checkAdviced(new HelloToby(), classMethodPointcut, true);
  }

  private void checkAdviced(Object target, Pointcut pointcut, boolean adviced) {
    ProxyFactoryBean pfBean = new ProxyFactoryBean();
    pfBean.setTarget(target);
    pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
    Hello proxiedHello = (Hello) pfBean.getObject();

    if (adviced) {
      assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
      assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
      assertThat(proxiedHello.sayThankYou("Toby"), is("Thank You Toby"));
    } else {
      assertThat(proxiedHello.sayHello("Toby"), is("Hello Toby"));
      assertThat(proxiedHello.sayHi("Toby"), is("Hi Toby"));
      assertThat(proxiedHello.sayThankYou("Toby"), is("Thank You Toby"));
    }
  }

}
