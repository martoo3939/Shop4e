package com.shop4e.shop.web.response;

public class AddressResponse {
  private String id;
  private String country;
  private String city;
  private String street;
  private String postalCode;
  private String description;

  public AddressResponse() {
  }

  public String getId() {
    return id;
  }

  public AddressResponse setId(String id) {
    this.id = id;
    return this;
  }

  public String getCountry() {
    return country;
  }

  public AddressResponse setCountry(String country) {
    this.country = country;
    return this;
  }

  public String getCity() {
    return city;
  }

  public AddressResponse setCity(String city) {
    this.city = city;
    return this;
  }

  public String getStreet() {
    return street;
  }

  public AddressResponse setStreet(String street) {
    this.street = street;
    return this;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public AddressResponse setPostalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public AddressResponse setDescription(String description) {
    this.description = description;
    return this;
  }
}
