package com.shop4e.shop.service.impl;

import static com.shop4e.shop.util.service.ProductUtil.TOTAL_IMAGES;

import com.shop4e.shop.domain.Book;
import com.shop4e.shop.domain.BookCategory;
import com.shop4e.shop.domain.Category;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.enumeration.BookLanguage;
import com.shop4e.shop.domain.enumeration.CurrencyType;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.BookRepository;
import com.shop4e.shop.service.BookService;
import com.shop4e.shop.service.CategoryService;
import com.shop4e.shop.util.UserUtil;
import com.shop4e.shop.util.mapper.ProductMapper;
import com.shop4e.shop.util.service.ProductUtil;
import com.shop4e.shop.web.request.BookCreationRequest;
import com.shop4e.shop.web.request.BookSearchRequest;
import com.shop4e.shop.web.request.BookUpdateRequest;
import com.shop4e.shop.web.response.BookResponse;
import com.shop4e.shop.web.response.PagedResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final CategoryService categoryService;
  private final UserUtil userUtil;
  private final ProductUtil productUtil;
  private final ProductMapper productMapper;

  public BookServiceImpl(
      BookRepository bookRepository,
      CategoryService categoryService,
      UserUtil userUtil,
      ProductUtil productUtil,
      ProductMapper productMapper) {
    this.bookRepository = bookRepository;
    this.categoryService = categoryService;
    this.userUtil = userUtil;
    this.productUtil = productUtil;
    this.productMapper = productMapper;
  }

  @Override
  public BookResponse createBook(BookCreationRequest request, Optional<MultipartFile[]> images,
      Authentication principal) {
    User seller = getUserFromPrincipal(principal);

    images.ifPresent(productUtil::checkImages);
    productUtil.checkCurrency(request);

    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
    Category category = categoryService.getCategoryOrFail(request);
    BookCategory bookCategory = categoryService.getBookCategoryOrFail(request);

    Book product = new Book();
    product.setSeller(seller);
    product.setTitle(request.getTitle());
    product.setDescription(request.getDescription());
    product.setCurrency(currency);
    product.setPrice(request.getPrice());
    product.setCategory(category);
    //Book specific
    product.setAuthor(request.getAuthor());
    product.setBookCategory(bookCategory);
    product.setISBN(request.getISBN());
    product.setPages(request.getPages());
    product.setPublishedAt(request.getPublishedAt());
    //TODO
    product.setLanguage(BookLanguage.valueOf(request.getLanguage()));

    productUtil.attachImages(product, images);

    bookRepository.saveAndFlush(product);

    BookResponse response = productMapper.mapBookToBookResponse(product);

    return response;
  }

  @Override
  public BookResponse updateBook(String bookId, BookUpdateRequest request,
      Optional<MultipartFile[]> images,
      Authentication principal) {
    User seller = getUserFromPrincipal(principal);

    images.ifPresent(productUtil::checkImages);
    productUtil.checkCurrency(request);

    CurrencyType currency = CurrencyType.getCurrency(request.getCurrency());
    BookCategory bookCategory = categoryService.getBookCategoryOrFail(request);

    Book product = bookRepository.findBookByIdAndSeller(UUID.fromString(bookId), seller)
        .orElseThrow(
            () -> new CustomException(String.format("Product with id: %s does not exist", bookId)));
    images.ifPresent(i -> {
      if (i.length + product.getPhotos().size() > TOTAL_IMAGES) {
        throw new CustomException("Total count of images exceeded.");
      }

      productUtil.attachImages(product, images);
    });
    product.setTitle(request.getTitle());
    product.setDescription(request.getDescription());
    product.setCurrency(currency);
    product.setPrice(request.getPrice());
    //Book specific
    product.setAuthor(request.getAuthor());
    product.setBookCategory(bookCategory);
    product.setISBN(request.getISBN());
    product.setPages(request.getPages());
    product.setPublishedAt(request.getPublishedAt());
    product.setLanguage(BookLanguage.valueOf(request.getLanguage()));

    bookRepository.saveAndFlush(product);

    BookResponse response = productMapper.mapBookToBookResponse(product);

    return response;
  }

  @Override
  public BookResponse getBook(String id) {
    Book book = bookRepository.findById(UUID.fromString(id))
        .orElseThrow(() -> new CustomException("Book not found."));

    return productMapper.mapBookToBookResponse(book);
  }

  @Override
  public PagedResponse searchBooks(BookSearchRequest request, Pageable pageable,
      Authentication principal) {
    Page<Book> searchResult = bookRepository.searchBooks(request, pageable);

    List<BookResponse> books = searchResult.stream().map(productMapper::mapBookToBookResponse)
        .collect(Collectors.toList());

    return new PagedResponse(books, searchResult.getTotalElements(), searchResult.getTotalPages());
  }

  private User getUserFromPrincipal(Authentication principal) {
    return userUtil.getUserFromPrincipal(principal);
  }
}
