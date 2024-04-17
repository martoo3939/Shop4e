package com.shop4e.shop.web.request;

import com.shop4e.shop.util.validator.PasswordMatches;
import com.shop4e.shop.util.validator.SecurePassword;
import com.shop4e.shop.util.validator.ValidEmail;
import javax.validation.constraints.NotBlank;

@PasswordMatches
public class RegistrationRequest {

  @NotBlank(message = "Username must not be blank.")
  private String username;
  @NotBlank(message = "First name must not be blank.")
  private String firstName;
  @NotBlank(message = "Last name must not be blank.")
  private String lastName;
  @SecurePassword
  @NotBlank(message = "Password must not be blank.")
  private String password;
  @NotBlank(message = "Confirm password must not be blank.")
  private String confirmPassword;
  @ValidEmail
  @NotBlank(message = "Email must not be blank.")
  private String email;

  public RegistrationRequest() {
  }

  public String getUsername() {
    return username;
  }

  public RegistrationRequest setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public RegistrationRequest setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public RegistrationRequest setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public RegistrationRequest setPassword(String password) {
    this.password = password;
    return this;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public RegistrationRequest setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public RegistrationRequest setEmail(String email) {
    this.email = email;
    return this;
  }
}
