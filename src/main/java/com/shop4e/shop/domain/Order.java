package com.shop4e.shop.domain;

import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.domain.enumeration.OrderStatus;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_orders")
public class Order extends Audit {

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  private BigDecimal price;
  @Enumerated(EnumType.ORDINAL)
  private CurrencyType currency;
  private Integer quantity;
  @ManyToOne
  private Address address;
  @ManyToOne
  private User orderer;
  @Enumerated(EnumType.ORDINAL)
  private OrderStatus status;
  @ManyToOne
  private User seller;

  public Order() {
  }

  public Product getProduct() {
    return product;
  }

  public Order setProduct(Product product) {
    this.product = product;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Order setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public CurrencyType getCurrency() {
    return currency;
  }

  public Order setCurrency(CurrencyType currency) {
    this.currency = currency;
    return this;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public Order setQuantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  public Address getAddress() {
    return address;
  }

  public Order setAddress(Address address) {
    this.address = address;
    return this;
  }

  public User getOrderer() {
    return orderer;
  }

  public Order setOrderer(User orderer) {
    this.orderer = orderer;
    return this;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public Order setStatus(OrderStatus status) {
    this.status = status;
    return this;
  }

  public User getSeller() {
    return seller;
  }

  public Order setSeller(User seller) {
    this.seller = seller;
    return this;
  }
}
