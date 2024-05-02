package com.shop4e.shop.domain.enumeration;

public enum BookLanguage {
  BULGARIAN("Bulgarian"), ENGLISH("English");

  private final String language;

  BookLanguage(String language) {
    this.language = language;
  }

  public String getLanguage() {
    return language;
  }
}
