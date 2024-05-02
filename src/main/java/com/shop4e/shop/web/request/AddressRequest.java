package com.shop4e.shop.web.request;

import javax.validation.constraints.NotBlank;

public class AddressRequest {
  @NotBlank
  private String country;
  @NotBlank
  private String city;
  @NotBlank
  private String street;
  @NotBlank
  private String postalCode;
  private String description;

  public AddressRequest() {
  }

  public String getCountry() {
    return country;
  }

  public AddressRequest setCountry(String country) {
    this.country = country;
    return this;
  }

  public String getCity() {
    return city;
  }

  public AddressRequest setCity(String city) {
    this.city = city;
    return this;
  }

  public String getStreet() {
    return street;
  }

  public AddressRequest setStreet(String street) {
    this.street = street;
    return this;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public AddressRequest setPostalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public AddressRequest setDescription(String description) {
    this.description = description;
    return this;
  }
}
