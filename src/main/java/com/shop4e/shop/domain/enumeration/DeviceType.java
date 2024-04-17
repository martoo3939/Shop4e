package com.shop4e.shop.domain.enumeration;

import java.util.Arrays;

public enum DeviceType {
  LAPTOP("Laptop"), DESKTOP("Desktop PC");

  private final String typeName;

  DeviceType(String typeName) {
    this.typeName = typeName;
  }

  public static boolean supportDevice(String type) {
    boolean hasType = Arrays.stream(DeviceType.values())
        .anyMatch(deviceType -> deviceType.name().equalsIgnoreCase(type));

    return hasType;
  }

  public static DeviceType getDeviceType(String type) {
    return Arrays.stream(DeviceType.values())
        .filter(deviceType -> deviceType.name().equalsIgnoreCase(type))
        .findFirst()
        .orElse(null);
  }

  public String getTypeName() {
    return typeName;
  }
}
