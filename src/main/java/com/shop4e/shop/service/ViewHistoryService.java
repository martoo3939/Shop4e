package com.shop4e.shop.service;

import com.shop4e.shop.domain.ProductViewHistory;
import com.shop4e.shop.domain.User;

public interface ViewHistoryService {

  ProductViewHistory getProductView(String productId, User viewer);

  ProductViewHistory saveProductView(String productId, User viewer);

  ProductViewHistory updateProductView(String productId, User viewer);

  ProductViewHistory logProductView(String productId, User viewer);
}
