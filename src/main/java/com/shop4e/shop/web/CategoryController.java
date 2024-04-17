package com.shop4e.shop.web;

import com.shop4e.shop.domain.Category;
import com.shop4e.shop.domain.Genre;
import com.shop4e.shop.service.CategoryService;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.web.response.CategoryResponse;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> allCategories() {
    List<Category> categories = categoryService.getAllCategories();

    return ResponseBuilder.buildResponse(HttpStatus.OK, categories);
  }

  @GetMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> bookCategories() {
    List<CategoryResponse> categories = categoryService.getBookCategories();

    return ResponseBuilder.buildResponse(HttpStatus.OK, categories);
  }

  @GetMapping(value = "/genre", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> entertainmentGenres() {
    List<Genre> genres = categoryService.getAllGenres();

    return ResponseBuilder.buildResponse(HttpStatus.OK, genres);
  }
}
