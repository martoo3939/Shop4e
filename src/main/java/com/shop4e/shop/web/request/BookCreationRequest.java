package com.shop4e.shop.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class BookCreationRequest extends ProductCreationRequest implements BookProductCategory {

  @NotBlank
  private String ISBN;
  @NotBlank
  private String author;
  @NotNull
  @DateTimeFormat(iso = ISO.DATE)
  private LocalDate publishedAt;
  @NotBlank
  private String pages;
  @NotBlank
  private String bookCategory;
  @NotBlank
  private String language;

  @JsonProperty("ISBN")
  public String getISBN() {
    return ISBN;
  }

  @JsonProperty("ISBN")
  public BookCreationRequest setISBN(String ISBN) {
    this.ISBN = ISBN;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public BookCreationRequest setAuthor(String author) {
    this.author = author;
    return this;
  }

  public LocalDate getPublishedAt() {
    return publishedAt;
  }

  public BookCreationRequest setPublishedAt(LocalDate publishedAt) {
    this.publishedAt = publishedAt;
    return this;
  }

  public String getPages() {
    return pages;
  }

  public BookCreationRequest setPages(String pages) {
    this.pages = pages;
    return this;
  }

  public String getBookCategory() {
    return bookCategory;
  }

  public BookCreationRequest setBookCategory(String bookCategory) {
    this.bookCategory = bookCategory;
    return this;
  }

  public String getLanguage() {
    return language;
  }

  public BookCreationRequest setLanguage(String language) {
    this.language = language;
    return this;
  }
}
