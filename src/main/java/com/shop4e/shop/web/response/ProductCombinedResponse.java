package com.shop4e.shop.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shop4e.shop.domain.ProductProjection;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.domain.enumeration.EntertainmentType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCombinedResponse {

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
  private Date publishedAt;
  private String brand;
  private String hardDrive;
  private String memory;
  private String processor;
  private String type;
  private String videoCard;
  private String bookCategoryId;
  private Boolean favourite;

  public ProductCombinedResponse() {
  }

  public ProductCombinedResponse(ProductProjection projection) {
    this.id = projection.getId();
    this.productType = projection.getProduct_Type();
    this.created = projection.getCreated();
    this.modified = projection.getModified();
    if (projection.getCurrency() != null) {
      CurrencyType[] currencyTypes = CurrencyType.values();
      this.currency = currencyTypes[projection.getCurrency()].name();
    }
    this.description = projection.getDescription();
    this.price = projection.getPrice();
    this.title = projection.getTitle();
    this.categoryId = projection.getCategory_Id();
    this.sellerId = projection.getSeller_Id();
    this.isbn = projection.getIsbn();
    this.author = projection.getAuthor();
    this.pages = projection.getPages();
    this.publishedAt = projection.getPublished_At();
    this.brand = projection.getBrand();
    this.hardDrive = projection.getHard_Drive();
    this.memory = projection.getMemory();
    this.processor = projection.getProcessor();
    if (projection.getType() != null) {
      EntertainmentType[] entertainmentTypes = EntertainmentType.values();
      this.type = entertainmentTypes[projection.getType()].name();
    }
    this.videoCard = projection.getVideo_Card();
    this.bookCategoryId = projection.getBook_Category_Id();
    if (projection.getFavourite() != null) {
      this.favourite = projection.getFavourite();
    } else {
      this.favourite = false;
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

  public Date getPublishedAt() {
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
}
