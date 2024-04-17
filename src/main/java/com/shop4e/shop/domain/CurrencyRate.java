package com.shop4e.shop.domain;

import com.shop4e.shop.domain.enumeration.CurrencyType;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "currency_rates")
public class CurrencyRate extends Audit {
  @Enumerated(EnumType.STRING)
  private CurrencyType targetCurrency;
  @Enumerated(EnumType.STRING)
  private CurrencyType sourceCurrency;
  private BigDecimal rate;

  public CurrencyRate() {
  }

  public CurrencyType getTargetCurrency() {
    return targetCurrency;
  }

  public CurrencyRate setTargetCurrency(
      CurrencyType targetCurrency) {
    this.targetCurrency = targetCurrency;
    return this;
  }

  public CurrencyType getSourceCurrency() {
    return sourceCurrency;
  }

  public CurrencyRate setSourceCurrency(
      CurrencyType sourceCurrency) {
    this.sourceCurrency = sourceCurrency;
    return this;
  }

  public BigDecimal getRate() {
    return rate;
  }

  public CurrencyRate setRate(BigDecimal rate) {
    this.rate = rate;
    return this;
  }
}
