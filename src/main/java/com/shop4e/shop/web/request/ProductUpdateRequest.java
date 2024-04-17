package com.shop4e.shop.web.request;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class ProductUpdateRequest implements ProductCurrency {
  @NotBlank
  protected String title;
  @NotBlank
  protected String description;
  @DecimalMin(value = "0.00")
  @Digits(integer = 10, fraction = 2)
  protected BigDecimal price;
  @NotBlank
  protected String currency;

  public ProductUpdateRequest() {
  }

  public String getTitle() {
    return title;
  }

  public ProductUpdateRequest setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public ProductUpdateRequest setDescription(String description) {
    this.description = description;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public ProductUpdateRequest setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public String getCurrency() {
    return currency;
  }

  public ProductUpdateRequest setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
}
