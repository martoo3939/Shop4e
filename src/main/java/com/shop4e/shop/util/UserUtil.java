package com.shop4e.shop.util;

import com.shop4e.shop.domain.User;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

  private final UserRepository userRepository;

  public UserUtil(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getUserFromPrincipal(Authentication principal) {
    UserDetails userDetails = (UserDetails) principal.getPrincipal();
    User user = userRepository.findUserByEmail(userDetails.getUsername()).orElse(null);

    if (user == null) {
      throw new CustomException("User not found!");
    }

    return user;
  }

  public boolean isUserVerified(Authentication principal) {
    User user = getUserFromPrincipal(principal);

    return user.isVerified() != null && user.isVerified();
  }
}
