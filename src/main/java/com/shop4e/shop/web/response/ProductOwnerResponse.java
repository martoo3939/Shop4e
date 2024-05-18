package com.shop4e.shop.web.response;

public class ProductOwnerResponse {
  private String productId;
  private Boolean productOwner;

  public ProductOwnerResponse() {
  }

  public String getProductId() {
    return productId;
  }

  public ProductOwnerResponse setProductId(String productId) {
    this.productId = productId;
    return this;
  }

  public Boolean getProductOwner() {
    return productOwner;
  }

  public ProductOwnerResponse setProductOwner(Boolean productOwner) {
    this.productOwner = productOwner;
    return this;
  }
}
