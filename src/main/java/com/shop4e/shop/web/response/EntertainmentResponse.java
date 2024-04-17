package com.shop4e.shop.web.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shop4e.shop.web.marker.ProductResponse;
import java.util.List;
import java.util.Map;

public class EntertainmentResponse implements ProductResponse {
  @JsonUnwrapped
  private ProductDetailsResponse generalDetails;
  private List<String> genres;
  private Map<String, String> details;
  private String type;

  public EntertainmentResponse() {
  }

  public ProductDetailsResponse getGeneralDetails() {
    return generalDetails;
  }

  public EntertainmentResponse setGeneralDetails(
      ProductDetailsResponse generalDetails) {
    this.generalDetails = generalDetails;
    return this;
  }

  public List<String> getGenres() {
    return genres;
  }

  public EntertainmentResponse setGenres(List<String> genres) {
    this.genres = genres;
    return this;
  }

  public Map<String, String> getDetails() {
    return details;
  }

  public EntertainmentResponse setDetails(Map<String, String> details) {
    this.details = details;
    return this;
  }

  public String getType() {
    return type;
  }

  public EntertainmentResponse setType(String type) {
    this.type = type;
    return this;
  }
}
