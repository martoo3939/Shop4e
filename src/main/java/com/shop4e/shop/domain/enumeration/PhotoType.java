package com.shop4e.shop.domain.enumeration;

public enum PhotoType {
  PROFILE("Profile Photo"),
  PRODUCT("Product Photo");
  private final String type;

  PhotoType(String type) {
    this.type = type;
  }
}
