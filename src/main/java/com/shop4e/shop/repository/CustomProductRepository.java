package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.ProductProjection;
import com.shop4e.shop.repository.util.ProductProjectionMapping;
import com.shop4e.shop.web.request.ProductFilter;
import com.shop4e.shop.web.response.ProductCombinedResponse;
import com.shop4e.shop.web.response.ProductFilterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomProductRepository {

  Page<Product> findAllByCategory(String categoryId, Pageable pageable);

  Page<ProductFilterResponse> findAllProducts(String userId, ProductFilter productFilter, Pageable pageable);

  Page<ProductFilterResponse> findAllProductsByGuest(ProductFilter productFilter, Pageable pageable);
}
