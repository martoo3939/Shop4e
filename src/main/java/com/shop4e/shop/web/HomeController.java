package com.shop4e.shop.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping(value = "/", produces = "text/html")
  public String home() {
    return "<h1>Hello world!</h1>";
  }

  @GetMapping(value = "/me")
  public String myProfile() {
    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();

    UserDetails currentUser = (UserDetails) authentication.getPrincipal();

    return currentUser.getUsername();
  }
}
