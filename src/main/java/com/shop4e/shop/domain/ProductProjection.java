package com.shop4e.shop.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public interface ProductProjection {
  String getId();
  String getProduct_Type();
  LocalDateTime getCreated();
  LocalDateTime getModified();
  Integer getCurrency();
  String getDescription();
  BigDecimal getPrice();
  String getTitle();
  String getCategory_Id();
  String getSeller_Id();
  String getIsbn();
  String getAuthor();
  String getPages();
  Date getPublished_At();
  String getBrand();
  String getHard_Drive();
  String getMemory();
  String getProcessor();
  Integer getType();
  String getVideo_Card();
  String getBook_Category_Id();
  Boolean getFavourite();
}
