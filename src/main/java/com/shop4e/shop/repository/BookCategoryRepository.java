package com.shop4e.shop.repository;

import com.shop4e.shop.domain.BookCategory;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, UUID> {

}
