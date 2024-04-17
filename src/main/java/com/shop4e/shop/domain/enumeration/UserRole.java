package com.shop4e.shop.domain.enumeration;

import javax.persistence.Embeddable;

public enum UserRole {
  USER("ROLE_USER"),
  SELLER("ROLE_SELLER"),
  ADMIN("ROLE_ADMIN");

  private final String role;

  UserRole(String role) {
    this.role = role;
  }

  public String getRoleName() {
    return role;
  }
}
