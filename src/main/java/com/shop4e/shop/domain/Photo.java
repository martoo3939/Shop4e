package com.shop4e.shop.domain;

import com.shop4e.shop.domain.enumeration.PhotoType;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Photo extends Audit {

  private String location;
  @Enumerated(EnumType.ORDINAL)
  private PhotoType type;
  private String identifier;
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;
  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Photo() {
  }

  public String getLocation() {
    return location;
  }

  public Photo setLocation(String location) {
    this.location = location;
    return this;
  }

  public PhotoType getType() {
    return type;
  }

  public Photo setType(PhotoType type) {
    this.type = type;
    return this;
  }

  public String getIdentifier() {
    return identifier;
  }

  public Photo setIdentifier(String identifier) {
    this.identifier = identifier;
    return this;
  }

  public Product getProduct() {
    return product;
  }

  public Photo setProduct(Product product) {
    this.product = product;
    return this;
  }

  public User getUser() {
    return user;
  }

  public Photo setUser(User user) {
    this.user = user;
    return this;
  }
}
