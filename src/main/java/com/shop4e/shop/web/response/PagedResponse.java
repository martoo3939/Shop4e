package com.shop4e.shop.web.response;

import java.util.List;

public class PagedResponse {
  private List<?> content;
  private long totalElements;
  private int totalPages;

  public PagedResponse() {
  }

  public PagedResponse(List<?> content, long totalElements, int totalPages) {
    this.content = content;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }

  public List<?> getContent() {
    return content;
  }

  public PagedResponse setContent(List<?> content) {
    this.content = content;
    return this;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public PagedResponse setTotalElements(long totalElements) {
    this.totalElements = totalElements;
    return this;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public PagedResponse setTotalPages(int totalPages) {
    this.totalPages = totalPages;
    return this;
  }
}
