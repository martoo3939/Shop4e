package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Book;
import com.shop4e.shop.web.request.BookSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomBookRepository {
  Page<Book> searchBooks(BookSearchRequest searchRequest, Pageable pageable);
}
