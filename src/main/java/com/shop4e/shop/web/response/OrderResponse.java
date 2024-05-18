package com.shop4e.shop.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.shop4e.shop.web.response.ProductDetailsResponse.Seller;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderResponse {
  private String id;
  private BigDecimal price;
  private Integer quantity;
  private String currency;
  private AddressResponse address;
  private ProductDetailsResponse product;
  private Seller orderer;
  private BigDecimal totalPrice;
  @JsonFormat(shape = Shape.STRING, pattern = "MMM, dd, yyyy, HH:mm")
  private LocalDateTime orderedAt;

  public OrderResponse() {
  }

  public BigDecimal getPrice() {
    return price;
  }

  public OrderResponse setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public OrderResponse setQuantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  public String getCurrency() {
    return currency;
  }

  public OrderResponse setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public AddressResponse getAddress() {
    return address;
  }

  public OrderResponse setAddress(AddressResponse address) {
    this.address = address;
    return this;
  }

  public ProductDetailsResponse getProduct() {
    return product;
  }

  public OrderResponse setProduct(ProductDetailsResponse product) {
    this.product = product;
    return this;
  }

  public Seller getOrderer() {
    return orderer;
  }

  public OrderResponse setOrderer(Seller orderer) {
    this.orderer = orderer;
    return this;
  }

  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  public OrderResponse setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
    return this;
  }

  public String getId() {
    return id;
  }

  public OrderResponse setId(String id) {
    this.id = id;
    return this;
  }

  public LocalDateTime getOrderedAt() {
    return orderedAt;
  }

  public OrderResponse setOrderedAt(LocalDateTime orderedAt) {
    this.orderedAt = orderedAt;
    return this;
  }
}
