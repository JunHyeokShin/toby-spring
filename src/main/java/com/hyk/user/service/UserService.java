package com.hyk.user.service;

import com.hyk.user.domain.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserService {

  void add(User user);

  @Transactional(readOnly = true)
  User get(String id);

  @Transactional(readOnly = true)
  List<User> getAll();

  void update(User user);

  void deleteAll();

  void upgradeLevels();

}
