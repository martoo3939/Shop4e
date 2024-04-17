package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.BookCategory;
import com.shop4e.shop.domain.Category;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.BookCategoryRepository;
import com.shop4e.shop.repository.CategoryRepository;
import com.shop4e.shop.repository.GenreRepository;
import com.shop4e.shop.service.CategoryService;
import com.shop4e.shop.web.request.BookProductCategory;
import com.shop4e.shop.web.request.Genre;
import com.shop4e.shop.web.request.ProductCategory;
import com.shop4e.shop.web.response.CategoryResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final BookCategoryRepository bookCategoryRepository;
  private final GenreRepository genreRepository;

  public CategoryServiceImpl(CategoryRepository categoryRepository,
      BookCategoryRepository bookCategoryRepository, GenreRepository genreRepository) {
    this.categoryRepository = categoryRepository;
    this.bookCategoryRepository = bookCategoryRepository;
    this.genreRepository = genreRepository;
  }

  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public List<CategoryResponse> getBookCategories() {
    List<BookCategory> categories = bookCategoryRepository.findAll();
    List<CategoryResponse> mappedBookCategories = categories.stream().map(bookCategory -> {
      CategoryResponse category = createCategory(bookCategory);

      if (bookCategory.getParentCategory() != null) {
        category.setMainCategory(createCategory(bookCategory.getParentCategory()));
      }

      return category;
    }).collect(Collectors.toList());

    return mappedBookCategories;
  }

  @Override
  public <T extends BookProductCategory> BookCategory getBookCategoryOrFail(T request) {
    BookCategory bookCategory = bookCategoryRepository.findById(
            UUID.fromString(request.getBookCategory()))
        .orElseThrow(() -> new CustomException("Book category not found."));

    return bookCategory;
  }

  @Override
  public <T extends Genre> List<com.shop4e.shop.domain.Genre> getGenresOrFail(T request) {
    List<String> genreIds = request.getGenres();
    List<com.shop4e.shop.domain.Genre> genres = genreRepository.findAllById(
        genreIds.stream().map(UUID::fromString).toList());

    return genres;
  }

  @Override
  public List<com.shop4e.shop.domain.Genre> getAllGenres() {
    return genreRepository.findAll();
  }

  @Override
  public <T extends ProductCategory> Category getCategoryOrFail(T request) {
    Category category = categoryRepository.findById(UUID.fromString(request.getCategory()))
        .orElseThrow(() -> new CustomException("Category not found!"));

    return category;
  }

  private CategoryResponse createCategory(BookCategory bookCategory) {
    CategoryResponse category = new CategoryResponse();
    category.setId(bookCategory.getId().toString());
    category.setName(bookCategory.getName());

    return category;
  }
}
