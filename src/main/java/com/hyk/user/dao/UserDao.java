package com.hyk.user.dao;

import com.hyk.user.domain.User;

import java.util.List;

public interface UserDao {

  void add(User user);

  User get(String id);

  List<User> getAll();

  void deleteAll();

  int getCount();

}
