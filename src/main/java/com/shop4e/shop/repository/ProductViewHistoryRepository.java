package com.shop4e.shop.repository;

import com.shop4e.shop.domain.ProductViewHistory;
import com.shop4e.shop.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductViewHistoryRepository extends JpaRepository<ProductViewHistory, UUID> {
  Optional<ProductViewHistory> findByProduct_IdAndViewer(UUID productId, User viewer);

  Optional<ProductViewHistory> findProductViewHistoryByProduct_IdAndViewer(UUID product_id,
      User viewer);

  @Query("SELECT p FROM ProductViewHistory p WHERE p.product.id = :productId AND p.viewer.id = :viewerId")
  Optional<ProductViewHistory> findProductViewHistory(@Param("productId") UUID productId, @Param("viewerId") UUID viewerId);
}
