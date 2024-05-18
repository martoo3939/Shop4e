package com.shop4e.shop.web.request;

import com.shop4e.shop.util.validator.NotEmptyList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EntertainmentUpdateRequest extends ProductUpdateRequest implements EntertainmentType, Genre {
  @NotNull
  @NotEmptyList
  private List<String> genres;
  @NotBlank
  private String duration;
  @NotNull
  private Date releaseDate;
  @NotBlank
  private String format;
  @NotBlank
  private String type;

  public EntertainmentUpdateRequest() {
  }

  @Override
  public List<String> getGenres() {
    return genres;
  }

  public EntertainmentUpdateRequest setGenres(List<String> genres) {
    this.genres = genres;
    return this;
  }

  public String getDuration() {
    return duration;
  }

  public EntertainmentUpdateRequest setDuration(String duration) {
    this.duration = duration;
    return this;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public EntertainmentUpdateRequest setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }

  public String getFormat() {
    return format;
  }

  public EntertainmentUpdateRequest setFormat(String format) {
    this.format = format;
    return this;
  }

  @Override
  public String getType() {
    return type;
  }

  public EntertainmentUpdateRequest setType(String type) {
    this.type = type;
    return this;
  }
}
