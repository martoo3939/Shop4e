package com.shop4e.shop.repository;

import com.shop4e.shop.domain.ProductViewHistory;
import com.shop4e.shop.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductViewHistoryRepository extends JpaRepository<ProductViewHistory, UUID> {
  Optional<ProductViewHistory> findByProduct_IdAndViewer(UUID productId, User viewer);
}
