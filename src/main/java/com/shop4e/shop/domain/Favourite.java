package com.shop4e.shop.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Favourite extends Audit {
  @ManyToOne
  private User user;
  @ManyToOne
  private Product product;
  private boolean favourite;

  public Favourite() {
  }

  public User getUser() {
    return user;
  }

  public Favourite setUser(User user) {
    this.user = user;
    return this;
  }

  public Product getProduct() {
    return product;
  }

  public Favourite setProduct(Product product) {
    this.product = product;
    return this;
  }

  public boolean isFavourite() {
    return favourite;
  }

  public Favourite setFavourite(boolean favourite) {
    this.favourite = favourite;
    return this;
  }
}
