package com.shop4e.shop.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.domain.enumeration.EntertainmentType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductFilterResponse {

  private String id;
  private String productType;
  private LocalDateTime created;
  private LocalDateTime modified;
  private String currency;
  private String description;
  private BigDecimal price;
  private String title;
  private String categoryId;
  private String sellerId;
  private String isbn;
  private String author;
  private String pages;
  private LocalDate publishedAt;
  private String brand;
  private String hardDrive;
  private String memory;
  private String processor;
  private String type;
  private String videoCard;
  private String bookCategoryId;
  private Boolean favourite;
  private List<String> images = new ArrayList<>();

  public ProductFilterResponse(
      String id,
      String product_type,
      LocalDateTime created,
      LocalDateTime modified,
      Integer currency,
      String description,
      BigDecimal price,
      String title,
      String categoryId,
      String seller_id,
      String isbn,
      String author,
      String pages,
      LocalDate published_at,
      String brand,
      String hard_drive,
      String memory,
      String processor,
      Integer type,
      String video_card,
      String book_category_id,
      Boolean favourite,
      String images
  ) {
    this.id = id;
    this.productType = product_type;
    this.created = created;
    this.modified = modified;
    if (currency != null) {
      CurrencyType[] currencyTypes = CurrencyType.values();
      this.currency = currencyTypes[currency].getType();
    }
    this.description = description;
    this.price = price;
    this.title = title;
    this.categoryId = categoryId;
    this.sellerId = seller_id;
    this.isbn = isbn;
    this.author = author;
    this.pages = pages;
    this.publishedAt = published_at;
    this.brand = brand;
    this.hardDrive = hard_drive;
    this.memory = memory;
    this.processor = processor;
    if (type != null) {
      EntertainmentType[] entertainmentTypes = EntertainmentType.values();
      this.type = entertainmentTypes[type].name();
    }
    this.videoCard = video_card;
    this.bookCategoryId = book_category_id;
    this.favourite = favourite;
    if (images != null) {
      this.images = Arrays.asList(images.split(","));
    }
  }

  public ProductFilterResponse(
      String id,
      String product_type,
      LocalDateTime created,
      LocalDateTime modified,
      Integer currency,
      String description,
      BigDecimal price,
      String title,
      String categoryId,
      String seller_id,
      String isbn,
      String author,
      String pages,
      LocalDate published_at,
      String brand,
      String hard_drive,
      String memory,
      String processor,
      Integer type,
      String video_card,
      String book_category_id,
      String images
  ) {
    this.id = id;
    this.productType = product_type;
    this.created = created;
    this.modified = modified;
    if (currency != null) {
      CurrencyType[] currencyTypes = CurrencyType.values();
      this.currency = currencyTypes[currency].name();
    }
    this.description = description;
    this.price = price;
    this.title = title;
    this.categoryId = categoryId;
    this.sellerId = seller_id;
    this.isbn = isbn;
    this.author = author;
    this.pages = pages;
    this.publishedAt = published_at;
    this.brand = brand;
    this.hardDrive = hard_drive;
    this.memory = memory;
    this.processor = processor;
    if (type != null) {
      EntertainmentType[] entertainmentTypes = EntertainmentType.values();
      this.type = entertainmentTypes[type].name();
    }
    this.videoCard = video_card;
    this.bookCategoryId = book_category_id;
    if (images != null) {
      this.images = Arrays.asList(images.split(","));
    }
  }

  public String getId() {
    return id;
  }

  public String getProductType() {
    return productType;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public String getCurrency() {
    return currency;
  }

  public String getDescription() {
    return description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getTitle() {
    return title;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public String getSellerId() {
    return sellerId;
  }

  public String getIsbn() {
    return isbn;
  }

  public String getAuthor() {
    return author;
  }

  public String getPages() {
    return pages;
  }

  public LocalDate getPublishedAt() {
    return publishedAt;
  }

  public String getBrand() {
    return brand;
  }

  public String getHardDrive() {
    return hardDrive;
  }

  public String getMemory() {
    return memory;
  }

  public String getProcessor() {
    return processor;
  }

  public String getType() {
    return type;
  }

  public String getVideoCard() {
    return videoCard;
  }

  public String getBookCategoryId() {
    return bookCategoryId;
  }

  public Boolean getFavourite() {
    return favourite;
  }

  public List<String> getImages() {
    return images;
  }
}
