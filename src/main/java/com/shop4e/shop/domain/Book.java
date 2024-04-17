package com.shop4e.shop.domain;

import com.shop4e.shop.domain.enumeration.BookLanguage;
import java.time.LocalDate;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("book")
public class Book extends Product {
  private String ISBN;
  private String author;
  private LocalDate publishedAt;
  private String pages;
  @ManyToOne
  private BookCategory bookCategory;
  @Enumerated(EnumType.ORDINAL)
  private BookLanguage language;

  public Book() {
  }

  public String getISBN() {
    return ISBN;
  }

  public Book setISBN(String ISBN) {
    this.ISBN = ISBN;
    return this;
  }

  public String getAuthor() {
    return author;
  }

  public Book setAuthor(String author) {
    this.author = author;
    return this;
  }

  public LocalDate getPublishedAt() {
    return publishedAt;
  }

  public Book setPublishedAt(LocalDate publishedAt) {
    this.publishedAt = publishedAt;
    return this;
  }

  public String getPages() {
    return pages;
  }

  public Book setPages(String pages) {
    this.pages = pages;
    return this;
  }

  public BookCategory getBookCategory() {
    return bookCategory;
  }

  public Book setBookCategory(BookCategory bookCategory) {
    this.bookCategory = bookCategory;
    return this;
  }

  public BookLanguage getLanguage() {
    return language;
  }

  public Book setLanguage(BookLanguage language) {
    this.language = language;
    return this;
  }
}
