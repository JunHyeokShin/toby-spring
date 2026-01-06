package com.hyk.user.service;

import com.hyk.user.domain.User;

public interface UserService {

  void add(User user);

  void upgradeLevels();

}
