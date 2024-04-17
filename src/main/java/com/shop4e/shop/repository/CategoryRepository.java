package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Category;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
  Optional<Category> findCategoryByName(String name);
}
