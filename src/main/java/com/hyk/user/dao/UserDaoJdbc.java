package com.hyk.user.dao;

import com.hyk.user.domain.Level;
import com.hyk.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDaoJdbc implements UserDao {

  private JdbcTemplate jdbcTemplate;
  private Map<String, String> sqlMap;
  private RowMapper<User> userMapper = new RowMapper<User>() {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User();
      user.setId(rs.getString("id"));
      user.setName(rs.getString("name"));
      user.setPassword(rs.getString("password"));
      user.setEmail(rs.getString("email"));
      user.setLevel(Level.valueOf(rs.getInt("level")));
      user.setLogin(rs.getInt("login"));
      user.setRecommend(rs.getInt("recommend"));
      return user;
    }
  };

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public void setSqlMap(Map<String, String> sqlMap) {
    this.sqlMap = sqlMap;
  }

  @Override
  public void add(User user) {
    this.jdbcTemplate.update(
        this.sqlMap.get("add"),
        user.getId(), user.getName(), user.getPassword(), user.getEmail(),
        user.getLevel().intValue(), user.getLogin(), user.getRecommend()
    );
  }

  @Override
  public User get(String id) {
    return this.jdbcTemplate.queryForObject(this.sqlMap.get("get"), new Object[]{id}, this.userMapper);
  }

  @Override
  public List<User> getAll() {
    return this.jdbcTemplate.query(this.sqlMap.get("getAll"), this.userMapper);
  }

  @Override
  public void update(User user) {
    this.jdbcTemplate.update(
        this.sqlMap.get("update"),
        user.getName(), user.getPassword(), user.getEmail(),
        user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getId()
    );
  }

  @Override
  public void deleteAll() {
    this.jdbcTemplate.update(this.sqlMap.get("deleteAll"));
  }

  @Override
  public int getCount() {
    // JdbcTemplate.queryForInt() 메소드는 4.2.0 버전 이후로 제거되었음
    return this.jdbcTemplate.queryForObject(this.sqlMap.get("getCount"), Integer.class);
  }

}
