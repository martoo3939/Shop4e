package com.shop4e.shop.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class BookCategory extends Audit {
  private String name;
  @ManyToOne
  private BookCategory parentCategory;

  public BookCategory() {
  }

  public String getName() {
    return name;
  }

  public BookCategory setName(String name) {
    this.name = name;
    return this;
  }

  public BookCategory getParentCategory() {
    return parentCategory;
  }

  public BookCategory setParentCategory(BookCategory parentCategory) {
    this.parentCategory = parentCategory;
    return this;
  }
}
