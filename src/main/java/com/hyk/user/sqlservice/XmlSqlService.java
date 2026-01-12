package com.hyk.user.sqlservice;

import com.hyk.user.dao.UserDao;
import com.hyk.user.sqlservice.jaxb.SqlType;
import com.hyk.user.sqlservice.jaxb.Sqlmap;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class XmlSqlService implements SqlService {

  private Map<String, String> sqlMap = new HashMap<>();

  public XmlSqlService() {
    String contextPath = Sqlmap.class.getPackage().getName();
    try {
      JAXBContext context = JAXBContext.newInstance(contextPath);
      Unmarshaller unmarshaller = context.createUnmarshaller();
      InputStream is = UserDao.class.getResourceAsStream("sqlmap.xml");
      Sqlmap sqlmap = (Sqlmap) unmarshaller.unmarshal(is);

      for (SqlType sql : sqlmap.getSql()) {
        sqlMap.put(sql.getKey(), sql.getValue());
      }
    } catch (JAXBException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getSql(String key) throws SqlRetrievalFailureException {
    String sql = sqlMap.get(key);
    if (sql == null) throw new SqlRetrievalFailureException(key + "에 대한 SQL을 찾을 수 없습니다");
    else return sql;
  }

}
