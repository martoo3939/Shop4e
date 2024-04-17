package com.shop4e.shop.service;

import com.shop4e.shop.web.request.FavouriteProductRequest;
import com.shop4e.shop.web.response.FavouriteProductResponse;
import com.shop4e.shop.web.response.PagedResponse;
import org.springframework.security.core.Authentication;

public interface WishlistService {

  FavouriteProductResponse favouriteProduct(FavouriteProductRequest request,
      Authentication principal);

  PagedResponse getFavouriteProducts(boolean favourite, int page, int size,
      Authentication principal);

  FavouriteProductResponse getFavouriteProduct(String id, Authentication principal);
}
