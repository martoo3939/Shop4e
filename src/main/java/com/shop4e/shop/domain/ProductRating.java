package com.shop4e.shop.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class ProductRating extends Audit {

  private Integer points;
  private String description;
  @ManyToOne
  private User ratedBy;

  public ProductRating() {
  }

  public Integer getPoints() {
    return points;
  }

  public ProductRating setPoints(Integer points) {
    this.points = points;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public ProductRating setDescription(String description) {
    this.description = description;
    return this;
  }

  public User getRatedBy() {
    return ratedBy;
  }

  public ProductRating setRatedBy(User ratedBy) {
    this.ratedBy = ratedBy;
    return this;
  }
}
