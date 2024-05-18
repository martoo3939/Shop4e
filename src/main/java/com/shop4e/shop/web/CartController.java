package com.shop4e.shop.web;

import com.shop4e.shop.service.CartService;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.web.response.PagedResponse;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
@Validated
public class CartController {

  private final CartService cartService;

  public CartController(CartService cartService) {
    this.cartService = cartService;
  }

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getProductsFromCart(
      @RequestParam(value = "page", required = false, defaultValue = "0") @Valid @Min(0) int page,
      @RequestParam(value = "size", required = false, defaultValue = "20") @Valid @Min(1) @Max(20) int size,
      Authentication principal
  ) {
    PagedResponse response = cartService.getCartProducts(page, size, principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<?> isProductAdded(@PathVariable String id, Authentication principal) {
    boolean isProductInside = cartService.checkProduct(id, principal);

    Map<String, Object> response = new HashMap<>();
    response.put("inside", isProductInside);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }

  @PostMapping(value = "/{id}")
  public ResponseEntity<?> addProductToCart(@PathVariable String id, Authentication principal) {
    cartService.addProduct(id, principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> removeProductFromCart(@PathVariable String id,
      Authentication principal) {
    cartService.removeProduct(id, principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK);
  }

  @DeleteMapping(value = "/clear")
  public ResponseEntity<?> clearProductsFromCart(Authentication principal) {
    cartService.clearProducts(principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK);
  }
}
