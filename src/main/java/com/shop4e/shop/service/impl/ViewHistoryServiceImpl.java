package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.ProductViewHistory;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.ProductRepository;
import com.shop4e.shop.repository.ProductViewHistoryRepository;
import com.shop4e.shop.service.ViewHistoryService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class ViewHistoryServiceImpl implements ViewHistoryService {

  private final ProductViewHistoryRepository historyRepository;
  private final ProductRepository productRepository;

  public ViewHistoryServiceImpl(ProductViewHistoryRepository historyRepository,
      ProductRepository productRepository) {
    this.historyRepository = historyRepository;
    this.productRepository = productRepository;
  }

  @Override
  public ProductViewHistory getProductView(String productId, User viewer) {
    ProductViewHistory productView = historyRepository.findProductViewHistory(
        UUID.fromString(productId), viewer.getId()).orElse(null);

    return productView;
  }

  @Override
  public ProductViewHistory saveProductView(String productId, User viewer) {
    Product product = productRepository.findById(UUID.fromString(productId))
        .orElseThrow(() -> new CustomException("Product not found!"));

    ProductViewHistory productView = new ProductViewHistory();
    productView.setViewer(viewer);
    productView.setProduct(product);

    return historyRepository.save(productView);
  }

  @Override
  public ProductViewHistory updateProductView(String productId, User viewer) {
    ProductViewHistory productView = getProductView(productId, viewer);

    if (productView != null) {
      productView = historyRepository.save(productView);
    }

    return productView;
  }

  @Override
  public ProductViewHistory logProductView(String productId, User viewer) {
    ProductViewHistory productView = getProductView(productId, viewer);

    if(productView == null) {
      Product product = productRepository.findById(UUID.fromString(productId))
          .orElseThrow(() -> new CustomException("Product not found!"));

      productView = new ProductViewHistory();
      productView.setViewer(viewer);
      productView.setProduct(product);
      productView.setFirstVisit(LocalDateTime.now(ZoneOffset.UTC));
    } else {
      productView.setLastVisit(LocalDateTime.now(ZoneOffset.UTC));
    }

    return historyRepository.save(productView);
  }
}
