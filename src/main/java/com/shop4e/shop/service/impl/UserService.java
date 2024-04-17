package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.User;
import com.shop4e.shop.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserByEmail(String email) {
    return userRepository.findUserByEmail(email).orElse(null);
  }
}
