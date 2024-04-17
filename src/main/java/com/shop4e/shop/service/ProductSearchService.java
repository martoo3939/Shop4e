package com.shop4e.shop.service;

import com.shop4e.shop.web.request.ProductSearchRequest;
import com.shop4e.shop.web.response.ProductSearchResponse;
import java.util.List;

public interface ProductSearchService {
  ProductSearchResponse searchProduct(ProductSearchRequest searchRequest);
}
