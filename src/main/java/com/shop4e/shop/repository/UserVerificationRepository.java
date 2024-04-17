package com.shop4e.shop.repository;

import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.UserVerification;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerification, UUID> {

  Optional<UserVerification> findByUser(User user);

  Optional<UserVerification> findByToken(String token);
}
