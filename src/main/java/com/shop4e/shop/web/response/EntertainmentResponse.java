package com.shop4e.shop.web.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shop4e.shop.web.marker.ProductResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class EntertainmentResponse implements ProductResponse {
  @JsonUnwrapped
  private ProductDetailsResponse generalDetails;
  private List<String> genres;
  private String duration;
  @JsonFormat(shape = Shape.STRING,  pattern = "yyyy-MM-dd")
  private Date releaseDate;
  private String format;
  private String type;
  private List<String> genreIds;

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

  public String getDuration() {
    return duration;
  }

  public EntertainmentResponse setDuration(String duration) {
    this.duration = duration;
    return this;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public EntertainmentResponse setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }

  public String getFormat() {
    return format;
  }

  public EntertainmentResponse setFormat(String format) {
    this.format = format;
    return this;
  }

  public String getType() {
    return type;
  }

  public EntertainmentResponse setType(String type) {
    this.type = type;
    return this;
  }

  public List<String> getGenreIds() {
    return genreIds;
  }

  public EntertainmentResponse setGenreIds(List<String> genreIds) {
    this.genreIds = genreIds;
    return this;
  }
}
