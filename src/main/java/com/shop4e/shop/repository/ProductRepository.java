package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.ProductProjection;
import com.shop4e.shop.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, CustomProductRepository {

  Optional<Product> findProductByIdAndSeller(UUID id, User seller);

  Page<Product> findAllByDeletedAtIsNull(Pageable pageable);

  //TODO create pageable using the product projection
  @Query(value = "SELECT p.*, (SELECT f.favourite FROM favourite f WHERE f.user_id = :user_id AND f.product_id = p.id) AS favourite FROM product p WHERE p.deleted_at IS NULL",
      countQuery = "SELECT count(*) FROM product p WHERE p.deleted_at IS NULL",
      nativeQuery = true)
  Page<ProductProjection> findAllProducts(@Param("user_id") String userId, Pageable pageable);

  Page<Product> findProductsByTitleContainingAndDeletedAtIsNull(String title, Pageable pageable);

  Page<Product> findProductsByTitleContainingAndCategory_IdAndDeletedAtIsNull(String title, UUID categoryId, Pageable pageable);

  Optional<Product> findProductByIdAndDeletedAtIsNull(UUID id);

  List<Product> findProductsByIdInAndDeletedAtIsNullOrderByCreated(List<UUID> productIds);
}
