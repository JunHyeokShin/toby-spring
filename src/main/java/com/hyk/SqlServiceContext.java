package com.hyk;

import com.hyk.user.sqlservice.OxmSqlService;
import com.hyk.user.sqlservice.SqlRegistry;
import com.hyk.user.sqlservice.SqlService;
import com.hyk.user.sqlservice.updatable.EmbeddedDbSqlRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

@Configuration
public class SqlServiceContext {

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
  public Unmarshaller unmarshaller() {
    Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
    marshaller.setContextPath("com.hyk.user.sqlservice.jaxb");
    return marshaller;
  }

  @Bean
  public DataSource embeddedDatabase() {
    return new EmbeddedDatabaseBuilder()
        .setName("embeddedDatabase")
        .setType(HSQL)
        .addScript("classpath:com/hyk/user/sqlservice/updatable/sqlRegistrySchema.sql")
        .build();
  }

}
