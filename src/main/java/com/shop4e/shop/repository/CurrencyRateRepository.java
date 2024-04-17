package com.shop4e.shop.repository;

import com.shop4e.shop.domain.CurrencyRate;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, UUID> {
  Optional<CurrencyRate> findBySourceCurrencyAndTargetCurrency(CurrencyType sourceCurrency,
      CurrencyType targetCurrency);
}
