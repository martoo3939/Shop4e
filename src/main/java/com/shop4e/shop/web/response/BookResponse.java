package com.shop4e.shop.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shop4e.shop.web.marker.ProductResponse;
import java.time.LocalDate;

public class BookResponse implements ProductResponse {

  @JsonUnwrapped
  private ProductDetailsResponse details;
  private String ISBN;
  private String author;
  private LocalDate publishedAt;
  private String pages;
  private String bookCategory;
  private String bookCategoryId;
  private String language;

  public BookResponse() {
  }

  public ProductDetailsResponse getDetails() {
    return details;
  }

  public BookResponse setDetails(ProductDetailsResponse details) {
    this.details = details;
    return this;
  }

  @JsonProperty("ISBN")
  public String getISBN() {
    return ISBN;
  }

  @JsonProperty("ISBN")
  public BookResponse setISBN(String ISBN) {
    this.ISBN = ISBN;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public BookResponse setAuthor(String author) {
    this.author = author;
    return this;
  }

  public LocalDate getPublishedAt() {
    return publishedAt;
  }

  public BookResponse setPublishedAt(LocalDate publishedAt) {
    this.publishedAt = publishedAt;
    return this;
  }

  public String getPages() {
    return pages;
  }

  public BookResponse setPages(String pages) {
    this.pages = pages;
    return this;
  }

  public String getBookCategory() {
    return bookCategory;
  }

  public BookResponse setBookCategory(String bookCategory) {
    this.bookCategory = bookCategory;
    return this;
  }

  public String getLanguage() {
    return language;
  }

  public BookResponse setLanguage(String language) {
    this.language = language;
    return this;
  }

  public String getBookCategoryId() {
    return bookCategoryId;
  }

  public BookResponse setBookCategoryId(String bookCategoryId) {
    this.bookCategoryId = bookCategoryId;
    return this;
  }
}
