package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Cart;
import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.CartRepository;
import com.shop4e.shop.repository.ProductRepository;
import com.shop4e.shop.service.CartService;
import com.shop4e.shop.util.UserUtil;
import com.shop4e.shop.util.mapper.ProductMapper;
import com.shop4e.shop.web.response.PagedResponse;
import com.shop4e.shop.web.response.ProductDetailsResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final UserUtil userUtil;
  private final ProductMapper productMapper;

  public CartServiceImpl(
      CartRepository cartRepository,
      ProductRepository productRepository,
      UserUtil userUtil,
      ProductMapper productMapper) {
    this.cartRepository = cartRepository;
    this.productRepository = productRepository;
    this.userUtil = userUtil;
    this.productMapper = productMapper;
  }

  @Override
  public void addProduct(String productId, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    Product product = productRepository.findById(UUID.fromString(productId))
        .orElseThrow(() -> new CustomException("Product not found."));

    Cart cart = cartRepository.findCartByUser(user).orElse(createCart(user));

    if (checkProductInsideCart(cart, product)) {
      return;
    }

    cart.addProduct(product);

    cartRepository.saveAndFlush(cart);
  }

  private Cart createCart(User user) {
    Cart newCart = new Cart();
    newCart.setUser(user);

    return newCart;
  }

  private boolean checkProductInsideCart(Cart cart, Product product) {
    return cart.getProducts().stream().anyMatch(p -> p.getId().equals(product.getId()));
  }

  @Override
  public void removeProduct(String productId, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    Product product = productRepository.findById(UUID.fromString(productId))
        .orElseThrow(() -> new CustomException("Product not found."));

    Cart cart = cartRepository.findCartByUser(user).orElse(createCart(user));

    if (!checkProductInsideCart(cart, product)) {
      return;
    }

    cart.removeProduct(product);

    cartRepository.saveAndFlush(cart);
  }

  @Override
  public void clearProducts(Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    Cart cart = cartRepository.findCartByUser(user).orElse(createCart(user));

    cart.getProducts().clear();

    cartRepository.saveAndFlush(cart);
  }

  @Override
  public PagedResponse getCartProducts(int page, int size, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    PageRequest pageable = PageRequest.of(page, size);

    Page<Product> products = cartRepository.findProductsByUserCart(user, pageable);

    List<ProductDetailsResponse> content = products.getContent().stream()
        .map(productMapper::mapProductToResponse)
        .collect(Collectors.toList());

    PagedResponse response = new PagedResponse();
    response.setContent(content);
    response.setTotalElements(products.getTotalElements());
    response.setTotalPages(products.getTotalPages());

    return response;
  }

  @Override
  public boolean checkProduct(String productId, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);

    Product product = productRepository.findById(UUID.fromString(productId))
        .orElseThrow(() -> new CustomException("Product not found."));

    Cart cart = cartRepository.findCartByUser(user).orElse(createCart(user));

    return checkProductInsideCart(cart, product);
  }
}
