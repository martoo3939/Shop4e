package com.shop4e.shop.domain;

import com.shop4e.shop.domain.enumeration.EntertainmentType;
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
  @ElementCollection
  @CollectionTable(name = "entertainment_details",
      joinColumns = {
          @JoinColumn(columnDefinition = "detail_id", referencedColumnName = "id", table = "product")})
  @MapKeyColumn(name = "detail_name")
  @Column(name = "detail_value")
  private Map<String, String> details;
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

  public Map<String, String> getDetails() {
    return details;
  }

  public Entertainment setDetails(Map<String, String> details) {
    this.details = details;
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
