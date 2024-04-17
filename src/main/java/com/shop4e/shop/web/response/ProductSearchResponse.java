package com.shop4e.shop.web.response;

import java.math.BigDecimal;
import java.util.List;

public class ProductSearchResponse {

  public List<ProductResponse> products;
  public Long totalResults;
  public Integer totalPages;

  public ProductSearchResponse() {
  }

  public List<ProductResponse> getProducts() {
    return products;
  }

  public ProductSearchResponse setProducts(
      List<ProductResponse> products) {
    this.products = products;
    return this;
  }

  public Long getTotalResults() {
    return totalResults;
  }

  public ProductSearchResponse setTotalResults(Long totalResults) {
    this.totalResults = totalResults;
    return this;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public ProductSearchResponse setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  public static class ProductResponse {

    private String id;
    private String title;
    private BigDecimal price;
    private String currency;
    private String category;
    private List<String> photos;

    public ProductResponse() {
    }

    public String getId() {
      return id;
    }

    public ProductResponse setId(String id) {
      this.id = id;
      return this;
    }

    public String getTitle() {
      return title;
    }

    public ProductResponse setTitle(String title) {
      this.title = title;
      return this;
    }

    public BigDecimal getPrice() {
      return price;
    }

    public ProductResponse setPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    public String getCurrency() {
      return currency;
    }

    public ProductResponse setCurrency(String currency) {
      this.currency = currency;
      return this;
    }

    public String getCategory() {
      return category;
    }

    public ProductResponse setCategory(String category) {
      this.category = category;
      return this;
    }

    public List<String> getPhotos() {
      return photos;
    }

    public ProductResponse setPhotos(List<String> photos) {
      this.photos = photos;
      return this;
    }
  }
}
