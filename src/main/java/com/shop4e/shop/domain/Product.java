package com.shop4e.shop.domain;

import com.shop4e.shop.domain.enumeration.CurrencyType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Product extends Audit {

  private String title;
  private String description;
  private BigDecimal price;
  @Enumerated(EnumType.ORDINAL)
  private CurrencyType currency;
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Photo> photos;
  @ManyToOne
  private Category category;
  @ManyToOne
  private User seller;
  private LocalDateTime deletedAt;
  @OneToMany
  private List<ProductRating> ratings;
  @Column(name = "product_type", insertable = false, updatable = false)
  private String productType;

  public Product() {
  }

  public String getTitle() {
    return title;
  }

  public Product setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Product setDescription(String description) {
    this.description = description;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Product setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public CurrencyType getCurrency() {
    return currency;
  }

  public Product setCurrency(CurrencyType currency) {
    this.currency = currency;
    return this;
  }

  public List<Photo> getPhotos() {
    return photos;
  }

  public Product setPhotos(List<Photo> photos) {
    this.photos = photos;
    return this;
  }

  public Category getCategory() {
    return category;
  }

  public Product setCategory(Category category) {
    this.category = category;
    return this;
  }

  public User getSeller() {
    return seller;
  }

  public Product setSeller(User seller) {
    this.seller = seller;
    return this;
  }

  public LocalDateTime getDeletedAt() {
    return deletedAt;
  }

  public Product setDeletedAt(LocalDateTime deletedAt) {
    this.deletedAt = deletedAt;
    return this;
  }

  public List<ProductRating> getRatings() {
    return ratings;
  }

  public Product setRatings(List<ProductRating> ratings) {
    this.ratings = ratings;
    return this;
  }

  public String getProductType() {
    return productType;
  }
}
