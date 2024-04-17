package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Cart;
import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

  Optional<Cart> findCartByUser(User user);

  @Query("SELECT p FROM Cart c JOIN c.products p WHERE c.user = :user")
  Page<Product> findProductsByUserCart(@Param("user") User user, Pageable pageable);
}
