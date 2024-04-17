package com.shop4e.shop.web.request;

import javax.validation.constraints.NotBlank;

public class DeviceUpdateRequest extends ProductUpdateRequest implements DeviceType {

  @NotBlank
  private String brand;
  @NotBlank
  private String type;
  @NotBlank
  private String processor;
  @NotBlank
  private String videoCard;
  @NotBlank
  private String memory;
  @NotBlank
  private String hardDrive;

  public DeviceUpdateRequest() {
  }

  public String getBrand() {
    return brand;
  }

  public DeviceUpdateRequest setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public String getType() {
    return type;
  }

  public DeviceUpdateRequest setType(String type) {
    this.type = type;
    return this;
  }

  public String getProcessor() {
    return processor;
  }

  public DeviceUpdateRequest setProcessor(String processor) {
    this.processor = processor;
    return this;
  }

  public String getVideoCard() {
    return videoCard;
  }

  public DeviceUpdateRequest setVideoCard(String videoCard) {
    this.videoCard = videoCard;
    return this;
  }

  public String getMemory() {
    return memory;
  }

  public DeviceUpdateRequest setMemory(String memory) {
    this.memory = memory;
    return this;
  }

  public String getHardDrive() {
    return hardDrive;
  }

  public DeviceUpdateRequest setHardDrive(String hardDrive) {
    this.hardDrive = hardDrive;
    return this;
  }
}
