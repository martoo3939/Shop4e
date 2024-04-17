package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.User;
import com.shop4e.shop.repository.UserRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findUserByEmail(email).orElse(null);

    if (user == null) {
      throw new UsernameNotFoundException("User with email: " + email + " not found!");
    }

    return new org.springframework.security.core.userdetails.User(email, user.getPassword(),
        getAuthorities(user));
  }

  private Collection<? extends GrantedAuthority> getAuthorities(User user) {
    return user.getRoles()
        .stream()
        .map(role -> new SimpleGrantedAuthority(role.getRole().getRoleName()))
        .collect(Collectors.toList());
  }
}
