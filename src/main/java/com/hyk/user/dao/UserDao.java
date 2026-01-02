package com.hyk.user.dao;

import com.hyk.user.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

  private JdbcTemplate jdbcTemplate;
  private DataSource dataSource;

  public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.dataSource = dataSource;
  }

  public void add(User user) {
    this.jdbcTemplate.update("INSERT INTO users(id, name, password) VALUES(?, ?, ?)",
        user.getId(), user.getName(), user.getPassword());
  }

  public User get(String id) throws SQLException {
    Connection c = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
      c = dataSource.getConnection();
      ps = c.prepareStatement("SELECT * FROM users WHERE id = ?");
      ps.setString(1, id);
      rs = ps.executeQuery();
      User user = null;
      if (rs.next()) {
        user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
      }
      if (user == null) throw new EmptyResultDataAccessException(1);
      return user;
    } catch (SQLException e) {
      throw e;
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
        }
      }
      if (ps != null) {
        try {
          ps.close();
        } catch (SQLException e) {
        }
      }
      if (c != null) {
        try {
          c.close();
        } catch (SQLException e) {
        }
      }
    }
  }

  public void deleteAll() {
    this.jdbcTemplate.update("DELETE FROM users");
  }

  public int getCount() {
    // JdbcTemplate.queryForInt() 메소드는 4.2.0 버전 이후로 제거되었음
    return this.jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
  }

}
