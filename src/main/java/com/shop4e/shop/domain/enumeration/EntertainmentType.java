package com.shop4e.shop.domain.enumeration;

import java.util.Arrays;

public enum EntertainmentType {
  FILM, MUSIC;

  public static boolean supportEntertainment(String type) {
    return Arrays.stream(EntertainmentType.values())
        .anyMatch(entertainmentType -> entertainmentType.name().equalsIgnoreCase(type));
  }

  public static EntertainmentType getEntertainmentType(String type) {
    return Arrays.stream(EntertainmentType.values())
        .filter(entertainmentType -> entertainmentType.name().equalsIgnoreCase(type))
        .findFirst()
        .orElse(null);
  }
}
