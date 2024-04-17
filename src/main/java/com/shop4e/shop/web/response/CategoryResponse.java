package com.shop4e.shop.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class CategoryResponse {
  private String id;
  private String name;
  @JsonInclude(Include.NON_NULL)
  private CategoryResponse mainCategory;

  public CategoryResponse() {
  }

  public String getId() {
    return id;
  }

  public CategoryResponse setId(String id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public CategoryResponse setName(String name) {
    this.name = name;
    return this;
  }

  public CategoryResponse getMainCategory() {
    return mainCategory;
  }

  public CategoryResponse setMainCategory(
      CategoryResponse mainCategory) {
    this.mainCategory = mainCategory;
    return this;
  }
}
