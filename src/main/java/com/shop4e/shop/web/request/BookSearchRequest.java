package com.shop4e.shop.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

@JsonInclude(Include.NON_NULL)
public class BookSearchRequest {

  private String ISBN;
  private String author;
  private String category;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate publishedFrom;
  @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate publishedTo;

  public BookSearchRequest() {
  }

  @JsonProperty("ISBN")
  public String getISBN() {
    return ISBN;
  }

  @JsonProperty("ISBN")
  public BookSearchRequest setISBN(String ISBN) {
    this.ISBN = ISBN;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public BookSearchRequest setAuthor(String author) {
    this.author = author;
    return this;
  }

  public String getCategory() {
    return category;
  }

  public BookSearchRequest setCategory(String category) {
    this.category = category;
    return this;
  }

  public LocalDate getPublishedFrom() {
    return publishedFrom;
  }

  public BookSearchRequest setPublishedFrom(LocalDate publishedFrom) {
    this.publishedFrom = publishedFrom;
    return this;
  }

  public LocalDate getPublishedTo() {
    return publishedTo;
  }

  public BookSearchRequest setPublishedTo(LocalDate publishedTo) {
    this.publishedTo = publishedTo;
    return this;
  }
}
