package com.shop4e.shop.web.request;

import javax.validation.constraints.NotBlank;

public class FavouriteProductRequest {
  @NotBlank
  private String id;
  private boolean favourite;

  public FavouriteProductRequest() {
  }

  public String getId() {
    return id;
  }

  public FavouriteProductRequest setId(String id) {
    this.id = id;
    return this;
  }

  public boolean isFavourite() {
    return favourite;
  }

  public FavouriteProductRequest setFavourite(boolean favourite) {
    this.favourite = favourite;
    return this;
  }
}
