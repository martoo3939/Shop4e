package com.shop4e.shop.domain;

import com.shop4e.shop.domain.enumeration.DeviceType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("electronics")
public class ElectronicDevice extends Product {
  private String brand;
  @Enumerated(EnumType.ORDINAL)
  private DeviceType type;
  private String processor;
  private String videoCard;
  private String memory;
  private String hardDrive;

  public ElectronicDevice() {
  }

  public String getBrand() {
    return brand;
  }

  public ElectronicDevice setBrand(String brand) {
    this.brand = brand;
    return this;
  }

  public DeviceType getType() {
    return type;
  }

  public ElectronicDevice setType(DeviceType type) {
    this.type = type;
    return this;
  }

  public String getProcessor() {
    return processor;
  }

  public ElectronicDevice setProcessor(String processor) {
    this.processor = processor;
    return this;
  }

  public String getVideoCard() {
    return videoCard;
  }

  public ElectronicDevice setVideoCard(String videoCard) {
    this.videoCard = videoCard;
    return this;
  }

  public String getMemory() {
    return memory;
  }

  public ElectronicDevice setMemory(String memory) {
    this.memory = memory;
    return this;
  }

  public String getHardDrive() {
    return hardDrive;
  }

  public ElectronicDevice setHardDrive(String hardDrive) {
    this.hardDrive = hardDrive;
    return this;
  }
}
