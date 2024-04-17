package com.shop4e.shop.web.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsResponse {
  private String id;
  private String title;
  private String description;
  private BigDecimal price;
  private String currency;
  private String category;
  private String categoryId;
  private String seller;
  private String productType;
  private LocalDateTime published;
  private LocalDateTime edited;
  private List<String> photos = new ArrayList<>();

  public ProductDetailsResponse() {
  }

  public String getId() {
    return id;
  }

  public ProductDetailsResponse setId(String id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public ProductDetailsResponse setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public ProductDetailsResponse setDescription(String description) {
    this.description = description;
    return this;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public ProductDetailsResponse setPrice(BigDecimal price) {
    this.price = price;
    return this;
  }

  public String getCurrency() {
    return currency;
  }

  public ProductDetailsResponse setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public String getCategory() {
    return category;
  }

  public ProductDetailsResponse setCategory(String category) {
    this.category = category;
    return this;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public ProductDetailsResponse setCategoryId(String categoryId) {
    this.categoryId = categoryId;
    return this;
  }

  public String getSeller() {
    return seller;
  }

  public ProductDetailsResponse setSeller(String seller) {
    this.seller = seller;
    return this;
  }

  public String getProductType() {
    return productType;
  }

  public ProductDetailsResponse setProductType(String productType) {
    this.productType = productType;
    return this;
  }

  public LocalDateTime getPublished() {
    return published;
  }

  public ProductDetailsResponse setPublished(LocalDateTime published) {
    this.published = published;
    return this;
  }

  public LocalDateTime getEdited() {
    return edited;
  }

  public ProductDetailsResponse setEdited(LocalDateTime edited) {
    this.edited = edited;
    return this;
  }

  public List<String> getPhotos() {
    return photos;
  }

  public ProductDetailsResponse setPhotos(List<String> photos) {
    this.photos = photos;
    return this;
  }
}
