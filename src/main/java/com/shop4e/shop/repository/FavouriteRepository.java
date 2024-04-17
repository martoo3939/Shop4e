package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Favourite;
import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, UUID> {

  Optional<Favourite> findFavouriteByUserAndProduct(User user, Product product);

  Page<Favourite> getFavouritesByUserAndFavouriteIs(User user, boolean favourite,
      Pageable pageable);

  Optional<Favourite> findFavouriteByUserAndProductId(User user, UUID product_id);
}
