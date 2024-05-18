package com.shop4e.shop.web.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.shop4e.shop.web.marker.ProductResponse;

public class DeviceResponse implements ProductResponse {

  @JsonUnwrapped
  private ProductDetailsResponse details;
  private String brand;
  private String type;
  private String processor;
  private String videoCard;
  private String memory;
  private String hardDrive;
  private String deviceType;

  public DeviceResponse() {
  }

  public ProductDetailsResponse getDetails() {
    return details;
  }

  public DeviceResponse setDetails(ProductDetailsResponse details) {
    this.details = details;
    return this;
  }

  public String getBrand() {
    return brand;
  }

  public DeviceResponse setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public String getType() {
    return type;
  }

  public DeviceResponse setType(String type) {
    this.type = type;
    return this;
  }

  public String getProcessor() {
    return processor;
  }

  public DeviceResponse setProcessor(String processor) {
    this.processor = processor;
    return this;
  }

  public String getVideoCard() {
    return videoCard;
  }

  public DeviceResponse setVideoCard(String videoCard) {
    this.videoCard = videoCard;
    return this;
  }

  public String getMemory() {
    return memory;
  }

  public DeviceResponse setMemory(String memory) {
    this.memory = memory;
    return this;
  }

  public String getHardDrive() {
    return hardDrive;
  }

  public DeviceResponse setHardDrive(String hardDrive) {
    this.hardDrive = hardDrive;
    return this;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public DeviceResponse setDeviceType(String deviceType) {
    this.deviceType = deviceType;
    return this;
  }
}
