package com.shop4e.shop.web;

import com.shop4e.shop.service.OrderService;
import com.shop4e.shop.util.GenericResponse;
import com.shop4e.shop.util.ResponseBuilder;
import com.shop4e.shop.web.request.OrderRequest;
import com.shop4e.shop.web.response.OrderResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @GetMapping("/all/my")
  public GenericResponse<List<OrderResponse>> getUserOrders(Authentication principal) {
    List<OrderResponse> orderResponse = orderService.getOrders(principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, orderResponse);
  }

  @GetMapping("/all/seller")
  public GenericResponse<List<OrderResponse>> getSellerOrders(Authentication principal) {
    List<OrderResponse> orderResponse = orderService.getSellerOrders(principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, orderResponse);
  }

  @PostMapping
  public GenericResponse<List<OrderResponse>> createOrder(@RequestBody @Valid List<OrderRequest> orders,
      Authentication principal) {
    List<OrderResponse> orderResponse = orderService.createOrder(orders, principal);

    return ResponseBuilder.buildTypedResponse(HttpStatus.OK, orderResponse);
  }
}
