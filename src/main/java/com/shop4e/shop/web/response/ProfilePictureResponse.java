package com.shop4e.shop.web.response;

public class ProfilePictureResponse {
  private String picture;

  public ProfilePictureResponse() {
  }

  public ProfilePictureResponse(String picture) {
    this.picture = picture;
  }

  public String getPicture() {
    return picture;
  }

  public ProfilePictureResponse setPicture(String picture) {
    this.picture = picture;
    return this;
  }
}
