package com.shop4e.shop.web;

import com.shop4e.shop.service.BookService;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.util.validator.ValidatorUtil;
import com.shop4e.shop.web.request.BookCreationRequest;
import com.shop4e.shop.web.request.BookSearchRequest;
import com.shop4e.shop.web.request.BookUpdateRequest;
import com.shop4e.shop.web.response.BookResponse;
import com.shop4e.shop.web.response.PagedResponse;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/book")
@Validated
public class BookController {

  private final BookService bookService;
  private final ValidatorUtil validator;

  public BookController(BookService bookService, ValidatorUtil validator) {
    this.bookService = bookService;
    this.validator = validator;
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> searchBooks(
      @RequestParam(value = "page", required = false, defaultValue = "0") @Valid @Min(0) int page,
      @RequestParam(value = "size", required = false, defaultValue = "20") @Valid @Min(0) @Max(20) int size,
      @RequestBody(required = false) Optional<BookSearchRequest> request,
      Authentication principal,
      Errors errors) {
    validator.checkForErrors(errors);

    PageRequest pageable = PageRequest.of(page, size);

    PagedResponse response = bookService.searchBooks(request.orElse(new BookSearchRequest()),
        pageable, principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> viewBook(@PathVariable String id) {
    BookResponse book = bookService.getBook(id);

    return ResponseBuilder.buildResponse(HttpStatus.OK, book);
  }

  @PreAuthorize("@userUtil.isUserVerified(authentication)")
  @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> createBook(
      @RequestPart(name = "data") @Valid BookCreationRequest request,
      @RequestPart(name = "images[]", required = false) Optional<List<MultipartFile>> images,
      Authentication principal,
      Errors errors
  ) {
    validator.checkForErrors(errors);

    BookResponse book = bookService.createBook(request, images, principal);

    return ResponseBuilder.buildResponse(HttpStatus.CREATED, book);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> updateBook(
      @PathVariable String id,
      @RequestPart(name = "data") @Valid BookUpdateRequest request,
      @RequestPart(name = "images[]", required = false) Optional<List<MultipartFile>> images,
      Authentication principal,
      Errors errors) {
    validator.checkForErrors(errors);

    BookResponse book = bookService.updateBook(id, request, images, principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK, book);
  }
}
