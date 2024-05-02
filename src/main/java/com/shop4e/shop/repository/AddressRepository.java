package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Address;
import com.shop4e.shop.domain.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
  Optional<Address> findByIdAndUser(UUID id, User user);

  List<Address> findAddressesByUser(User user);
}
