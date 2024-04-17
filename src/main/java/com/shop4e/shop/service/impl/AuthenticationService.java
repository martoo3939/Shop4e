package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Role;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.enumeration.UserRole;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.UserRepository;
import com.shop4e.shop.service.UserVerificationService;
import com.shop4e.shop.util.jwt.JwtUtil;
import com.shop4e.shop.util.jwt.JwtUtil.Token;
import com.shop4e.shop.web.request.AuthenticationRequest;
import com.shop4e.shop.web.request.RegistrationRequest;
import java.util.Set;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;
  private final UserVerificationService userVerificationService;

  public AuthenticationService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder,
      JwtUtil jwtUtil,
      UserVerificationService userVerificationService) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
    this.userVerificationService = userVerificationService;
  }

  public UserDetails login(AuthenticationRequest request) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );

    return (UserDetails) authentication.getPrincipal();
  }

  public UserDetails register(RegistrationRequest request) {
    if (checkEmailAlreadyRegistered(request.getEmail())) {
      throw new CustomException("Email already exists.");
    }

    Role userRole = new Role();
    userRole.setRole(UserRole.USER);

    User user = new User();
    user.setFirstName(request.getFirstName())
        .setLastName(request.getLastName())
        .setUsername(request.getUsername())
        .setEmail(request.getEmail())
        .setPassword(passwordEncoder.encode(request.getPassword()))
        .setRoles(Set.of(userRole));

    userRepository.saveAndFlush(user);

    userVerificationService.doVerification(user);

    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

    return userDetails;
  }

  private boolean checkEmailAlreadyRegistered(String email) {
    return userRepository.findUserByEmail(email).isPresent();
  }

  public Token refreshToken(String refreshToken) {
    String username = jwtUtil.extractUsername(refreshToken);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    Boolean tokenValid = jwtUtil.isTokenValid(refreshToken, userDetails);

    if(!tokenValid) {
      throw new CustomException("Invalid refresh token.");
    }

    return jwtUtil.generateToken(userDetails);
  }

  public Boolean validateToken(String token) {
    String username = jwtUtil.extractUsername(token);
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    return jwtUtil.isTokenValid(token, userDetails);
  }
}
