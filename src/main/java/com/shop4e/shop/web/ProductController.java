package com.shop4e.shop.web;

import com.shop4e.shop.service.impl.ShopService;
import com.shop4e.shop.util.GenericResponse;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.util.validator.ValidOptionalParam;
import com.shop4e.shop.util.validator.ValidatorUtil;
import com.shop4e.shop.web.marker.ProductResponse;
import com.shop4e.shop.web.request.ProductCreationRequest;
import com.shop4e.shop.web.request.ProductFilter;
import com.shop4e.shop.web.request.ProductSortRequest;
import com.shop4e.shop.web.request.SortDirection;
import com.shop4e.shop.web.response.PagedResponse;
import com.shop4e.shop.web.response.ProductDetailsResponse;
import com.shop4e.shop.web.response.ProductOwnerResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
@Validated
public class ProductController {

  private final ShopService shopService;
  private final ValidatorUtil validator;

  public ProductController(ShopService shopService, ValidatorUtil validator) {
    this.shopService = shopService;
    this.validator = validator;
  }

  @GetMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public GenericResponse<ProductResponse> getProduct(@PathVariable String productId) {
    ProductResponse product = shopService.getProductById(productId);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, product);
  }

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public GenericResponse<PagedResponse> viewAllProducts(
      @RequestParam(value = "page", required = false, defaultValue = "0") @Valid @Min(0) int page,
      @RequestParam(value = "size", required = false, defaultValue = "20") @Valid @Min(1) @Max(20) int size,
      @RequestParam(name = "sort", required = false) Optional<String[]> sortParams,
      Authentication principal
  ) {
    List<ProductSortRequest> sortItems = extractSortParams(sortParams);

    PagedResponse products = shopService.getAllProducts(page, size, sortItems, principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, products);
  }

  @GetMapping(value = "/allFiltered", produces = MediaType.APPLICATION_JSON_VALUE)
  public GenericResponse<PagedResponse> viewAllProductsByFilter(
      @RequestParam(value = "page", required = false, defaultValue = "0") @Valid @Min(0) int page,
      @RequestParam(value = "size", required = false, defaultValue = "20") @Valid @Min(1) @Max(20) int size,
      @RequestParam(name = "sort", required = false) Optional<String[]> sortParams,
      @RequestParam(name = "title", required = false) Optional<String> title,
      @RequestParam(name = "priceFrom", required = false) Optional<BigDecimal> priceFrom,
      @RequestParam(name = "priceTo", required = false) Optional<BigDecimal> priceTo,
      @RequestParam(name = "category", required = false) Optional<String> categoryId,
      @RequestParam(name = "seller", required = false) Optional<String> sellerId,
      @RequestParam(name = "currency", required = false) Optional<String> currency,
      Authentication principal
  ) {
    List<ProductSortRequest> sortItems = extractSortParams(sortParams);

    ProductFilter filter = new ProductFilter();
    filter.setTitle(title.orElse(null))
        .setCurrency(currency.orElse(null))
        .setPriceFrom(priceFrom.orElse(null))
        .setPriceTo(priceTo.orElse(null))
        .setCategoryId(categoryId.orElse(null))
        .setSellerId(sellerId.orElse(null));

    PagedResponse products = shopService.getAllFilteredProducts(page, size, filter, sortItems,
        principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, products);
  }

  @GetMapping(value = "/guest/allFiltered")
  public GenericResponse<PagedResponse> viewAllProductsByFilterAsGuest(
      @RequestParam(value = "page", required = false, defaultValue = "0") @Valid @Min(0) int page,
      @RequestParam(value = "size", required = false, defaultValue = "20") @Valid @Min(1) @Max(20) int size,
      @RequestParam(name = "sort", required = false) Optional<String[]> sortParams,
      @RequestParam(name = "title", required = false) Optional<String> title,
      @RequestParam(name = "priceFrom", required = false) Optional<BigDecimal> priceFrom,
      @RequestParam(name = "priceTo", required = false) Optional<BigDecimal> priceTo,
      @RequestParam(name = "category", required = false) Optional<String> categoryId,
      @RequestParam(name = "seller", required = false) Optional<String> sellerId,
      @RequestParam(name = "currency", required = false) Optional<String> currency
  ) {
    List<ProductSortRequest> sortItems = extractSortParams(sortParams);

    ProductFilter filter = new ProductFilter();
    filter.setTitle(title.orElse(null))
        .setCurrency(currency.orElse(null))
        .setPriceFrom(priceFrom.orElse(null))
        .setPriceTo(priceTo.orElse(null))
        .setCategoryId(categoryId.orElse(null))
        .setSellerId(sellerId.orElse(null));

    PagedResponse products = shopService.getAllFilteredProductsByGuest(page, size, filter, sortItems);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, products);
  }

  @GetMapping(params = {"page", "size"}, produces = MediaType.APPLICATION_JSON_VALUE)
  public GenericResponse<PagedResponse> viewProducts(
      @RequestParam(value = "page") @Valid @Min(0) int page,
      @RequestParam(value = "size") @Valid @Min(1) @Max(20) int size,
      @RequestParam(value = "category", required = false) @ValidOptionalParam Optional<String> category) {
    PagedResponse products = shopService.getProducts(page, size, category.orElse(null));

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, products);
  }

//  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//  public GenericResponse<ProductDetailsResponse> viewProduct(@PathVariable String id) {
//    ProductDetailsResponse product = shopService.getProduct(id);
//
//    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, product);
//  }

  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public GenericResponse<ProductDetailsResponse> updateProduct(
      @PathVariable String id,
      @RequestBody @Valid ProductCreationRequest request,
      Authentication principal,
      Errors errors) {
    validator.checkForErrors(errors);

    ProductDetailsResponse response = shopService.updateProduct(id, request, principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, response);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable String id, Authentication principal) {
    shopService.deleteProduct(id, principal);

    return ResponseBuilder.buildResponse(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping(value = "/image/{imageId}")
  public ResponseEntity<?> deleteProductImage(@PathVariable String imageId, Authentication principal) {
    shopService.deleteProductImage(imageId, principal);

    return ResponseBuilder.buildResponse(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/owner/{id}")
  public GenericResponse<ProductOwnerResponse> checkProductOwner(@PathVariable String id, Authentication principal) {
    ProductOwnerResponse response = shopService.getProductOwner(id, principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, response);
  }

  private List<ProductSortRequest> extractSortParams(Optional<String[]> sortParams) {
    List<ProductSortRequest> sortItems = new ArrayList<>();
    if (sortParams.isPresent()) {
      for (final String sort : sortParams.get()) {
        String[] split = sort.split(":");
        if (split.length == 2 && split[0] != null && split[1] != null) {
          ProductSortRequest sortItem = new ProductSortRequest();
          sortItem.setField(split[0].trim());
          sortItem.setDirection(SortDirection.valueOf(split[1].trim()));

          sortItems.add(sortItem);
        }
      }
    }

    return sortItems;
  }
}
