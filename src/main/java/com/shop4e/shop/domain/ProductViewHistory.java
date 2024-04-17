package com.shop4e.shop.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_history")
public class ProductViewHistory extends Audit {

  @ManyToOne
  private User viewer;
  @ManyToOne
  private Product product;

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
}
