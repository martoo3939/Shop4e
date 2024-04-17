package com.shop4e.shop.domain.enumeration;

import java.math.BigDecimal;
import java.util.Arrays;

public enum CurrencyType {
  BGN("BGN", "lev", BigDecimal.valueOf(1.9558)),
  EURO("EUR", "€", BigDecimal.ONE);
  private final String type;
  private final String symbol;
  private final BigDecimal value;

  public String getType() {
    return type;
  }

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getValue() {
    return value;
  }

  CurrencyType(String type, String symbol, BigDecimal value) {
    this.type = type;
    this.symbol = symbol;
    this.value = value;
  }

  public static boolean supportCurrency(String currency) {
    boolean hasCurrency = Arrays.stream(CurrencyType.values())
        .anyMatch(currencyType -> currencyType.type.equals(currency));

    return hasCurrency;
  }

  public static CurrencyType getCurrency(String currency) {
    return Arrays.stream(CurrencyType.values())
        .filter(currencyType -> currencyType.type.equals(currency))
        .findFirst()
        .orElse(CurrencyType.BGN);
  }
}
