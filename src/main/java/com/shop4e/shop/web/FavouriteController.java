package com.shop4e.shop.web;

import com.shop4e.shop.service.WishlistService;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.util.validator.ValidatorUtil;
import com.shop4e.shop.web.request.FavouriteProductRequest;
import com.shop4e.shop.web.response.FavouriteProductResponse;
import com.shop4e.shop.web.response.PagedResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/favourite")
@Validated
public class FavouriteController {

  private final WishlistService wishlistService;
  private final ValidatorUtil validator;

  public FavouriteController(WishlistService wishlistService, ValidatorUtil validator) {
    this.wishlistService = wishlistService;
    this.validator = validator;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getFavouriteProducts(
      @RequestParam(value = "page") @Valid @Min(0) int page,
      @RequestParam(value = "size") @Valid @Min(1) @Max(20) int size,
      @RequestParam(name = "favourite", required = false, defaultValue = "true") boolean isFavourite,
      Authentication principal
  ) {
    PagedResponse response = wishlistService.getFavouriteProducts(isFavourite, page, size,
        principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getFavouriteProduct(@PathVariable String id, Authentication principal) {
    FavouriteProductResponse response = wishlistService.getFavouriteProduct(id, principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }

  @PostMapping
  public ResponseEntity<?> favouriteProduct(
      @RequestBody @Valid FavouriteProductRequest request,
      Authentication principal,
      Errors errors) {
    validator.checkForErrors(errors);

    FavouriteProductResponse response = wishlistService.favouriteProduct(request,
        principal);

    return ResponseBuilder.buildResponse(HttpStatus.OK, response);
  }
}
