package com.hyk;

import com.hyk.user.dao.UserDao;
import com.hyk.user.dao.UserDaoJdbc;
import com.hyk.user.service.UserService;
import com.hyk.user.service.UserServiceImpl;
import com.hyk.user.sqlservice.OxmSqlService;
import com.hyk.user.sqlservice.SqlRegistry;
import com.hyk.user.sqlservice.SqlService;
import com.hyk.user.sqlservice.updatable.EmbeddedDbSqlRegistry;
import com.mysql.cj.jdbc.Driver;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ImportResource("/applicationContext.xml")
public class ApplicationContext {

  @Resource
  DataSource embeddedDatabase;

  @Bean
  public UserService userService() {
    UserServiceImpl service = new UserServiceImpl();
    service.setUserDao(userDao());
    service.setMailSender(mailSender());
    return service;
  }

  @Bean
  public UserDao userDao() {
    UserDaoJdbc dao = new UserDaoJdbc();
    dao.setDataSource(dataSource());
    dao.setSqlService(sqlService());
    return dao;
  }

  @Bean
  public MailSender mailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost("mail.server.com");
    return mailSender;
  }

  @Bean
  public SqlService sqlService() {
    OxmSqlService sqlService = new OxmSqlService();
    sqlService.setSqlRegistry(sqlRegistry());
    sqlService.setUnmarshaller(unmarshaller());
    return sqlService;
  }

  @Bean
  public SqlRegistry sqlRegistry() {
    EmbeddedDbSqlRegistry sqlRegistry = new EmbeddedDbSqlRegistry();
    sqlRegistry.setDataSource(this.embeddedDatabase);
    return sqlRegistry;
  }

  @Bean
  public Unmarshaller unmarshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("com.hyk.user.sqlservice.jaxb");
    return marshaller;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    DataSourceTransactionManager tm = new DataSourceTransactionManager();
    tm.setDataSource(dataSource());
    return tm;
  }

  @Bean
  public DataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    dataSource.setDriverClass(Driver.class);
    dataSource.setUrl("jdbc:mysql://localhost:3306/springbook");
    dataSource.setUsername("root");
    dataSource.setPassword("password1234");
    return dataSource;
  }

}
