package com.shop4e.shop.web.response;

public class ProfileResponse {

  private String username;
  private String firstName;
  private String lastName;
  private String picture;
  private String email;
  private boolean isVerified;

  public ProfileResponse() {
  }

  public String getUsername() {
    return username;
  }

  public ProfileResponse setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public ProfileResponse setFirstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public ProfileResponse setLastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public String getPicture() {
    return picture;
  }

  public ProfileResponse setPicture(String picture) {
    this.picture = picture;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public ProfileResponse setEmail(String email) {
    this.email = email;
    return this;
  }

  public boolean isVerified() {
    return isVerified;
  }

  public ProfileResponse setVerified(boolean verified) {
    isVerified = verified;
    return this;
  }
}
