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
  private Seller seller;
  private String productType;
  private LocalDateTime published;
  private LocalDateTime edited;
  private List<Photo> photos = new ArrayList<>();

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

  public Seller getSeller() {
    return seller;
  }

  public ProductDetailsResponse setSeller(Seller seller) {
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

  public List<Photo> getPhotos() {
    return photos;
  }

  public ProductDetailsResponse setPhotos(List<Photo> photos) {
    this.photos = photos;
    return this;
  }

  public static class Seller {
    private String id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String picture;

    public Seller() {
    }

    public String getId() {
      return id;
    }

    public Seller setId(String id) {
      this.id = id;
      return this;
    }

    public String getEmail() {
      return email;
    }

    public Seller setEmail(String email) {
      this.email = email;
      return this;
    }

    public String getUsername() {
      return username;
    }

    public Seller setUsername(String username) {
      this.username = username;
      return this;
    }

    public String getFirstName() {
      return firstName;
    }

    public Seller setFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public String getLastName() {
      return lastName;
    }

    public Seller setLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public String getPicture() {
      return picture;
    }

    public Seller setPicture(String picture) {
      this.picture = picture;
      return this;
    }
  }

  public static class Photo {
    private String location;
    private String identifier;

    public Photo() {
    }

    public String getLocation() {
      return location;
    }

    public Photo setLocation(String location) {
      this.location = location;
      return this;
    }

    public String getIdentifier() {
      return identifier;
    }

    public Photo setIdentifier(String identifier) {
      this.identifier = identifier;
      return this;
    }
  }
}
