package com.shop4e.shop.web.request;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class ProductCreationRequest implements ProductCurrency, ProductCategory {

  @NotBlank
  protected String title;
  @NotBlank
  protected String description;
  @DecimalMin(value = "0.00")
  @Digits(integer = 10, fraction = 2)
  protected BigDecimal price;
  @NotBlank
  protected String currency;
  @NotBlank
  protected String category;

  public ProductCreationRequest() {
  }

  public String getTitle() {
    return title;
  }

  public ProductCreationRequest setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public ProductCreationRequest setDescription(String description) {
    this.description = description;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public ProductCreationRequest setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public String getCurrency() {
    return currency;
  }

  public ProductCreationRequest setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public String getCategory() {
    return category;
  }

  public ProductCreationRequest setCategory(String category) {
    this.category = category;
    return this;
  }
}
