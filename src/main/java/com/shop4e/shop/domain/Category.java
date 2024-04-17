package com.shop4e.shop.domain;

import javax.persistence.Entity;

@Entity
public class Category extends Audit {

  private String name;

  public Category() {
  }

  public String getName() {
    return name;
  }

  public Category setName(String name) {
    this.name = name;
    return this;
  }
}
