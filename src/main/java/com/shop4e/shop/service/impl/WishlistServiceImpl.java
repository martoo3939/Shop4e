package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Favourite;
import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.FavouriteRepository;
import com.shop4e.shop.repository.ProductRepository;
import com.shop4e.shop.service.WishlistService;
import com.shop4e.shop.util.UserUtil;
import com.shop4e.shop.web.request.FavouriteProductRequest;
import com.shop4e.shop.web.response.FavouriteProductResponse;
import com.shop4e.shop.web.response.PagedResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {

  private final FavouriteRepository favouriteRepository;
  private final ProductRepository productRepository;
  private final UserUtil userUtil;

  public WishlistServiceImpl(FavouriteRepository favouriteRepository,
      ProductRepository productRepository,
      UserUtil userUtil) {
    this.favouriteRepository = favouriteRepository;
    this.productRepository = productRepository;
    this.userUtil = userUtil;
  }

  @Override
  public FavouriteProductResponse favouriteProduct(FavouriteProductRequest request,
      Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    Product product = productRepository.findById(UUID.fromString(request.getId()))
        .orElseThrow(() -> new CustomException("No such product found."));

    Favourite favourite = favouriteRepository.findFavouriteByUserAndProduct(user, product)
        .orElse(new Favourite());
    favourite.setProduct(product);
    favourite.setUser(user);
    favourite.setFavourite(request.isFavourite());

    favouriteRepository.saveAndFlush(favourite);

    FavouriteProductResponse response = new FavouriteProductResponse(
        favourite.getProduct().getId().toString(), favourite.isFavourite());

    return response;
  }

  @Override
  public PagedResponse getFavouriteProducts(boolean favourite, int page, int size,
      Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    PageRequest pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "created"));

    Page<Favourite> favouriteProducts = favouriteRepository.getFavouritesByUserAndFavouriteIs(
        user, favourite, pageable);

    List<FavouriteProductResponse> favouriteMappedProducts = favouriteProducts.stream()
        .map(favouriteProduct -> new FavouriteProductResponse(
            favouriteProduct.getProduct().getId().toString(),
            favouriteProduct.isFavourite()))
        .collect(Collectors.toList());

    PagedResponse response = new PagedResponse();
    response.setContent(favouriteMappedProducts);
    response.setTotalElements(favouriteProducts.getTotalElements());
    response.setTotalPages(favouriteProducts.getTotalPages());

    return response;
  }

  @Override
  public FavouriteProductResponse getFavouriteProduct(String id, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    Favourite favourite = favouriteRepository.findFavouriteByUserAndProductId(user,
        UUID.fromString(id)).orElse(null);

    FavouriteProductResponse response =
        (favourite == null) ? new FavouriteProductResponse(id, false)
            : new FavouriteProductResponse(favourite.getProduct().getId().toString(),
                favourite.isFavourite());

    return response;
  }
}
