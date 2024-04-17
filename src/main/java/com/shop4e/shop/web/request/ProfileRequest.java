package com.shop4e.shop.web.request;

import javax.validation.constraints.NotBlank;

public class ProfileRequest {

  @NotBlank
  private String username;
  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;

  public ProfileRequest() {
  }

  public String getUsername() {
    return username;
  }

  public ProfileRequest setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public ProfileRequest setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public ProfileRequest setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }
}
