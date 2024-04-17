package com.shop4e.shop.domain;

import com.shop4e.shop.domain.enumeration.UserRole;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class Role {
  @Enumerated(EnumType.STRING)
  private UserRole role;

  public Role() {
  }

  public UserRole getRole() {
    return role;
  }

  public Role setRole(UserRole role) {
    this.role = role;
    return this;
  }
}
