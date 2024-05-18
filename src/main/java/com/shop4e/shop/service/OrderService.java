package com.shop4e.shop.service;

import com.shop4e.shop.web.request.OrderRequest;
import com.shop4e.shop.web.response.OrderResponse;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface OrderService {
  List<OrderResponse> createOrder(List<OrderRequest> orders, Authentication principal);

  List<OrderResponse> getOrders(Authentication principal);

  List<OrderResponse> getSellerOrders(Authentication principal);
}
