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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
@EnableTransactionManagement
public class ApplicationContext {

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
    sqlRegistry.setDataSource(embeddedDatabase());
    return sqlRegistry;
  }

  @Bean
  public DataSource embeddedDatabase() {
    return new EmbeddedDatabaseBuilder()
        .setName("embeddedDatabase")
        .setType(HSQL)
        .addScript("classpath:com/hyk/user/sqlservice/updatable/sqlRegistrySchema.sql")
        .build();
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
