package com.shop4e.shop.web.request;

public class ProductSortRequest {
  private String field;
  private SortDirection direction;

  public ProductSortRequest() {
  }

  public String getField() {
    return field;
  }

  public ProductSortRequest setField(String field) {
    this.field = field;
    return this;
  }

  public SortDirection getDirection() {
    return direction;
  }

  public ProductSortRequest setDirection(SortDirection direction) {
    this.direction = direction;
    return this;
  }
}
