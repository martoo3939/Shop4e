package com.shop4e.shop.web.response;

public class FavouriteProductResponse {
  private String id;
  private boolean favourite;

  public FavouriteProductResponse() {
  }

  public FavouriteProductResponse(String id, boolean favourite) {
    this.id = id;
    this.favourite = favourite;
  }

  public String getId() {
    return id;
  }

  public FavouriteProductResponse setId(String id) {
    this.id = id;
    return this;
  }

  public boolean isFavourite() {
    return favourite;
  }

  public FavouriteProductResponse setFavourite(boolean favourite) {
    this.favourite = favourite;
    return this;
  }
}
