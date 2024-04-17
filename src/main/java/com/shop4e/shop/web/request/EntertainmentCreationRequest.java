package com.shop4e.shop.web.request;

import com.shop4e.shop.util.validator.NotEmptyList;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EntertainmentCreationRequest extends ProductCreationRequest implements EntertainmentType, Genre {
  @NotNull
  @NotEmptyList
  private List<String> genres;
  @NotNull
  private Map<String, String> details;
  @NotBlank
  private String type;

  public EntertainmentCreationRequest() {
  }

  public List<String> getGenres() {
    return genres;
  }

  public EntertainmentCreationRequest setGenres(List<String> genres) {
    this.genres = genres;
    return this;
  }

  public Map<String, String> getDetails() {
    return details;
  }

  public EntertainmentCreationRequest setDetails(
      Map<String, String> details) {
    this.details = details;
    return this;
  }

  @Override
  public String getType() {
    return type;
  }

  public EntertainmentCreationRequest setType(String type) {
    this.type = type;
    return this;
  }
}
