package com.shop4e.shop.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Address extends Audit {
  private String country;
  private String city;
  private String street;
  private String postalCode;
  private String description;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public Address() {
  }

  public String getCountry() {
    return country;
  }

  public Address setCountry(String country) {
    this.country = country;
    return this;
  }

  public String getCity() {
    return city;
  }

  public Address setCity(String city) {
    this.city = city;
    return this;
  }

  public String getStreet() {
    return street;
  }

  public Address setStreet(String street) {
    this.street = street;
    return this;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public Address setPostalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Address setDescription(String description) {
    this.description = description;
    return this;
  }

  public User getUser() {
    return user;
  }

  public Address setUser(User user) {
    this.user = user;
    return this;
  }
}
