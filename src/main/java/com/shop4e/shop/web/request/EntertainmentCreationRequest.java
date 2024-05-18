package com.shop4e.shop.web.request;

import com.shop4e.shop.util.validator.NotEmptyList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class EntertainmentCreationRequest extends ProductCreationRequest implements EntertainmentType, Genre {
  @NotNull
  @NotEmptyList
  private List<String> genres;
  @NotBlank
  private String duration;
  @NotNull
  @DateTimeFormat(iso = ISO.DATE)
  private Date releaseDate;
  @NotBlank
  private String format;
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

  public String getDuration() {
    return duration;
  }

  public EntertainmentCreationRequest setDuration(String duration) {
    this.duration = duration;
    return this;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public EntertainmentCreationRequest setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }

  public String getFormat() {
    return format;
  }

  public EntertainmentCreationRequest setFormat(String format) {
    this.format = format;
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
