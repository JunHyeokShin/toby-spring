package com.hyk.user.dao;

import com.hyk.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {

  private JdbcTemplate jdbcTemplate;
  private RowMapper<User> userMapper = new RowMapper<User>() {
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User();
      user.setId(rs.getString("id"));
      user.setName(rs.getString("name"));
      user.setPassword(rs.getString("password"));
      return user;
    }
  };

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public void add(User user) {
    this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?, ?, ?)",
        user.getId(), user.getName(), user.getPassword());
  }

  public User get(String id) {
    return this.jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[]{id}, this.userMapper);
  }

  public void deleteAll() {
    this.jdbcTemplate.update("DELETE FROM users");
  }

  public int getCount() {
    // JdbcTemplate.queryForInt() 메소드는 4.2.0 버전 이후로 제거되었음
    return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
  }

  public List<User> getAll() {
    return this.jdbcTemplate.query("SELECT * FROM users ORDER BY id", this.userMapper);
  }

}
