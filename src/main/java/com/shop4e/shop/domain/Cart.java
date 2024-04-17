package com.shop4e.shop.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cart extends Audit {
  @OneToMany
  private List<Product> products = new ArrayList<>();
  @OneToOne
  private User user;

  public Cart() {
  }

  public List<Product> getProducts() {
    return products;
  }

  public Cart setProducts(List<Product> products) {
    this.products = products;
    return this;
  }

  public User getUser() {
    return user;
  }

  public Cart setUser(User user) {
    this.user = user;
    return this;
  }
}
