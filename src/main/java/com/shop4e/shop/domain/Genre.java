package com.shop4e.shop.domain;

import javax.persistence.Entity;

@Entity
public class Genre extends Audit {
  private String name;

  public Genre() {
  }

  public String getName() {
    return name;
  }

  public Genre setName(String name) {
    this.name = name;
    return this;
  }
}
