package com.shop4e.shop.domain;

import com.shop4e.shop.domain.enumeration.EntertainmentType;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;

@Entity
@DiscriminatorValue("entertainment")
public class Entertainment extends Product {

  @ManyToMany
  private List<Genre> genre;
  private String duration;
  private Date releaseDate;
  private String format;
  @Enumerated(EnumType.ORDINAL)
  private EntertainmentType type;

  public Entertainment() {
  }

  public List<Genre> getGenre() {
    return genre;
  }

  public Entertainment setGenre(List<Genre> genre) {
    this.genre = genre;
    return this;
  }

  public String getDuration() {
    return duration;
  }

  public Entertainment setDuration(String duration) {
    this.duration = duration;
    return this;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public Entertainment setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }

  public String getFormat() {
    return format;
  }

  public Entertainment setFormat(String format) {
    this.format = format;
    return this;
  }

  public EntertainmentType getType() {
    return type;
  }

  public Entertainment setType(EntertainmentType type) {
    this.type = type;
    return this;
  }
}
