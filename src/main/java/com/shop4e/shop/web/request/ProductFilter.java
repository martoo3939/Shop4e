package com.shop4e.shop.web.request;

import java.math.BigDecimal;

public class ProductFilter {
  private String title;
  private BigDecimal priceFrom;
  private BigDecimal priceTo;
  private String categoryId;
  private String sellerId;
  private String currency;

  public ProductFilter() {
  }

  public String getTitle() {
    return title;
  }

  public ProductFilter setTitle(String title) {
    this.title = title;
    return this;
  }

  public BigDecimal getPriceFrom() {
    return priceFrom;
  }

  public ProductFilter setPriceFrom(BigDecimal priceFrom) {
    this.priceFrom = priceFrom;
    return this;
  }

  public BigDecimal getPriceTo() {
    return priceTo;
  }

  public ProductFilter setPriceTo(BigDecimal priceTo) {
    this.priceTo = priceTo;
    return this;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public ProductFilter setCategoryId(String categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  public String getSellerId() {
    return sellerId;
  }

  public ProductFilter setSellerId(String sellerId) {
    this.sellerId = sellerId;
    return this;
  }

  public String getCurrency() {
    return currency;
  }

  public ProductFilter setCurrency(String currency) {
    this.currency = currency;
    return this;
  }
}
