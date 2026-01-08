package com.hyk.user.service;

import com.hyk.user.domain.User;

import java.util.List;

public interface UserService {

  void add(User user);

  User get(String id);

  List<User> getAll();

  void update(User user);

  void deleteAll();

  void upgradeLevels();

}
