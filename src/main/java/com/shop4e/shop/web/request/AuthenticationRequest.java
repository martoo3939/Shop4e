package com.shop4e.shop.web.request;

import com.shop4e.shop.util.validator.ValidEmail;
import javax.validation.constraints.NotBlank;

public class AuthenticationRequest {

  @ValidEmail
  @NotBlank(message = "Email must not be blank.")
  private String email;
  @NotBlank(message = "Password must not be blank.")
  private String password;

  public AuthenticationRequest() {
  }

  public String getEmail() {
    return email;
  }

  public AuthenticationRequest setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public AuthenticationRequest setPassword(String password) {
    this.password = password;
    return this;
  }
}
