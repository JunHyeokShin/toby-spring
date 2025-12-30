package com.hyk.user.dao;

import com.hyk.user.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class UserDaoConnectionCountingTest {

  public static void main(String[] args) throws ClassNotFoundException, SQLException {
    ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
    UserDao dao = context.getBean("userDao", UserDao.class);

    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.setId("" + i);
      user.setName("" + i);
      user.setPassword("" + i);
      dao.add(user);
    }

    CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
    System.out.println("Connection counter : " + ccm.getCounter());
  }

}
