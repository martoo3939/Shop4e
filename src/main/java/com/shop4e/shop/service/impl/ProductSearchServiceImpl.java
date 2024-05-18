package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Photo;
import com.shop4e.shop.domain.Product;
import com.shop4e.shop.repository.ProductRepository;
import com.shop4e.shop.service.ProductSearchService;
import com.shop4e.shop.web.request.ProductSearchRequest;
import com.shop4e.shop.web.response.ProductSearchResponse;
import com.shop4e.shop.web.response.ProductSearchResponse.ProductResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {

  private static final Integer TOTAL_SEARCH_RESULTS = 5;
  private final ProductRepository productRepository;

  public ProductSearchServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductSearchResponse searchProduct(ProductSearchRequest searchRequest) {
    PageRequest pageable = PageRequest.of(searchRequest.getPage(), TOTAL_SEARCH_RESULTS);
    Page<Product> result;
    if (searchRequest.getCategoryId() != null) {
      result = productRepository.findProductsByTitleContainingAndCategory_IdAndDeletedAtIsNull(
          searchRequest.getSearch(),
          UUID.fromString(searchRequest.getCategoryId()), pageable);
    } else {
      result = productRepository.findProductsByTitleContainingAndDeletedAtIsNull(searchRequest.getSearch(), pageable);
    }

    return mapProductResultToProductSearchResponse(result);
  }

  private ProductSearchResponse mapProductResultToProductSearchResponse(Page<Product> products) {
    ProductSearchResponse response = new ProductSearchResponse();
    response.setTotalResults(products.getTotalElements());
    response.setTotalPages(products.getTotalPages());
    response.setProducts(
        products.get().map(this::mapProductToProductResponse).collect(Collectors.toList()));
    return response;
  }

  private ProductSearchResponse.ProductResponse mapProductToProductResponse(Product product) {
    ProductResponse productResponse = new ProductResponse();
    productResponse.setId(product.getId().toString());
    productResponse.setTitle(product.getTitle());
    productResponse.setCategory(product.getCategory().getName());
    productResponse.setPrice(product.getPrice());
    productResponse.setCurrency(product.getCurrency().name());
    List<String> productPhotos = product.getPhotos().stream().map(Photo::getLocation)
        .collect(Collectors.toList());
    productResponse.setPhotos(productPhotos);

    return productResponse;
  }
}
