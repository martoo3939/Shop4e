package com.shop4e.shop.repository;

import com.shop4e.shop.domain.ElectronicDevice;
import com.shop4e.shop.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectronicDeviceRepository extends JpaRepository<ElectronicDevice, UUID> {
  Optional<ElectronicDevice> findElectronicDeviceByIdAndSeller(UUID id, User seller);
}
