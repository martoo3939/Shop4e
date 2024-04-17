package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Book;
import com.shop4e.shop.domain.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>, CustomBookRepository {

  Optional<Book> findBookByIdAndSeller(UUID id, User seller);
}
