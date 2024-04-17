package com.shop4e.shop.web.request;

import javax.validation.constraints.NotBlank;

public class DeviceCreationRequest extends ProductCreationRequest implements DeviceType {
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

  public DeviceCreationRequest() {
  }

  public String getBrand() {
    return brand;
  }

  public DeviceCreationRequest setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public String getType() {
    return type;
  }

  public DeviceCreationRequest setType(String type) {
    this.type = type;
    return this;
  }

  public String getProcessor() {
    return processor;
  }

  public DeviceCreationRequest setProcessor(String processor) {
    this.processor = processor;
    return this;
  }

  public String getVideoCard() {
    return videoCard;
  }

  public DeviceCreationRequest setVideoCard(String videoCard) {
    this.videoCard = videoCard;
    return this;
  }

  public String getMemory() {
    return memory;
  }

  public DeviceCreationRequest setMemory(String memory) {
    this.memory = memory;
    return this;
  }

  public String getHardDrive() {
    return hardDrive;
  }

  public DeviceCreationRequest setHardDrive(String hardDrive) {
    this.hardDrive = hardDrive;
    return this;
  }
}
