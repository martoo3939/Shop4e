package com.shop4e.shop.domain;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class UserVerification extends Audit {
  @OneToOne
  private User user;
  private String token;

  public UserVerification() {
  }

  public User getUser() {
    return user;
  }

  public UserVerification setUser(User user) {
    this.user = user;
    return this;
  }

  public String getToken() {
    return token;
  }

  public UserVerification setToken(String token) {
    this.token = token;
    return this;
  }
}
