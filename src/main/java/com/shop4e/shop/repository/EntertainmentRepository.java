package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Entertainment;
import com.shop4e.shop.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntertainmentRepository extends JpaRepository<Entertainment, UUID> {
  Optional<Entertainment> findEntertainmentByIdAndSeller(UUID id, User seller);
}
