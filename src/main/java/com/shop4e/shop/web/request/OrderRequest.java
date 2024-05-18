package com.shop4e.shop.web.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderRequest {
  @NotBlank
  private String productId;
  @NotNull
  @Min(1)
  private Integer quantity;
  @NotBlank
  private String addressId;

  public OrderRequest() {
  }

  public String getProductId() {
    return productId;
  }

  public OrderRequest setProductId(String productId) {
    this.productId = productId;
    return this;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public OrderRequest setQuantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  public String getAddressId() {
    return addressId;
  }

  public OrderRequest setAddressId(String addressId) {
    this.addressId = addressId;
    return this;
  }
}
