package com.shop4e.shop.service;

import com.shop4e.shop.domain.BookCategory;
import com.shop4e.shop.domain.Category;
import com.shop4e.shop.domain.Genre;
import com.shop4e.shop.web.request.BookProductCategory;
import com.shop4e.shop.web.request.ProductCategory;
import com.shop4e.shop.web.response.CategoryResponse;
import java.util.List;

public interface CategoryService {

  List<Category> getAllCategories();

  List<CategoryResponse> getBookCategories();

  <T extends BookProductCategory> BookCategory getBookCategoryOrFail(T request);

  <T extends com.shop4e.shop.web.request.Genre> List<Genre> getGenresOrFail(T request);

  List<Genre> getAllGenres();

  <T extends ProductCategory> Category getCategoryOrFail(T request);
}
