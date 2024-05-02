package com.shop4e.shop.service;

import com.shop4e.shop.web.request.BookCreationRequest;
import com.shop4e.shop.web.request.BookSearchRequest;
import com.shop4e.shop.web.request.BookUpdateRequest;
import com.shop4e.shop.web.response.BookResponse;
import com.shop4e.shop.web.response.PagedResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

public interface BookService {

  BookResponse createBook(BookCreationRequest request, Optional<List<MultipartFile>> images, Authentication principal);

  BookResponse updateBook(String bookId, BookUpdateRequest request, Optional<List<MultipartFile>> images, Authentication principal);

  BookResponse getBook(String id);

  PagedResponse searchBooks(BookSearchRequest request, Pageable pageable, Authentication principal);
}
