package com.shop4e.shop.web;

import com.shop4e.shop.service.ProductSearchService;
import com.shop4e.shop.util.GenericResponse;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.web.request.ProductSearchRequest;
import com.shop4e.shop.web.response.ProductSearchResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

  private final ProductSearchService productSearchService;

  public SearchController(ProductSearchService productSearchService) {
    this.productSearchService = productSearchService;
  }

  @PostMapping(value = "/product", produces = MediaType.APPLICATION_JSON_VALUE)
  public GenericResponse<ProductSearchResponse> searchProduct(
      @RequestBody @Valid ProductSearchRequest searchRequest) {
    ProductSearchResponse searchResult = productSearchService.searchProduct(
        searchRequest);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, searchResult);
  }
}
