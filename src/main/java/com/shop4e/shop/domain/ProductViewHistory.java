package com.shop4e.shop.domain;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "product_history", uniqueConstraints = {
    @UniqueConstraint(name = "uniqueViewHistory", columnNames = {"viewer_id", "product_id"})
})
public class ProductViewHistory extends Audit {

  @ManyToOne
  private User viewer;
  @ManyToOne
  private Product product;

  private LocalDateTime firstVisit;

  private LocalDateTime lastVisit;

  public ProductViewHistory() {
  }

  public User getViewer() {
    return viewer;
  }

  public ProductViewHistory setViewer(User viewer) {
    this.viewer = viewer;
    return this;
  }

  public Product getProduct() {
    return product;
  }

  public ProductViewHistory setProduct(Product product) {
    this.product = product;
    return this;
  }

  public LocalDateTime getFirstVisit() {
    return firstVisit;
  }

  public ProductViewHistory setFirstVisit(LocalDateTime firstVisit) {
    this.firstVisit = firstVisit;
    return this;
  }

  public LocalDateTime getLastVisit() {
    return lastVisit;
  }

  public ProductViewHistory setLastVisit(LocalDateTime lastVisit) {
    this.lastVisit = lastVisit;
    return this;
  }
}
