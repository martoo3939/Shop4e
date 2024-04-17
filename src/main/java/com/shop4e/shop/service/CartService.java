package com.shop4e.shop.service;

import com.shop4e.shop.web.response.PagedResponse;
import org.springframework.security.core.Authentication;

public interface CartService {

  void addProduct(String productId, Authentication principal);

  void removeProduct(String productId, Authentication principal);

  void clearProducts(Authentication principal);

  PagedResponse getCartProducts(int page, int size, Authentication principal);
}
