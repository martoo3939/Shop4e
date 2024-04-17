package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Photo;
import com.shop4e.shop.domain.User;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, UUID> {
  @Transactional
  void deletePhotoByIdentifier(String identifier);

  Optional<Photo> findPhotoByIdentifierAndProduct_Seller(String identifier, User product_seller);
}
