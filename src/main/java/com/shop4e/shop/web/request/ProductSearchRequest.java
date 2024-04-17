package com.shop4e.shop.web.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductSearchRequest {
  @NotBlank
  @Size(min = 3)
  private String search;
  private String categoryId;
  @Min(0)
  private Integer page = 0;

  public ProductSearchRequest() {
  }

  public String getSearch() {
    return search;
  }

  public ProductSearchRequest setSearch(String search) {
    this.search = search;
    return this;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public ProductSearchRequest setCategoryId(String categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  public Integer getPage() {
    return page;
  }

  public ProductSearchRequest setPage(Integer page) {
    this.page = page;
    return this;
  }
}
