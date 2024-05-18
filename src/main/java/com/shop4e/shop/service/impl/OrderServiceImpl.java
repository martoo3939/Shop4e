package com.shop4e.shop.service.impl;

import com.shop4e.shop.domain.Address;
import com.shop4e.shop.domain.Order;
import com.shop4e.shop.domain.Product;
import com.shop4e.shop.domain.User;
import com.shop4e.shop.domain.enumeration.OrderStatus;
import com.shop4e.shop.exception.CustomException;
import com.shop4e.shop.repository.AddressRepository;
import com.shop4e.shop.repository.OrderRepository;
import com.shop4e.shop.repository.ProductRepository;
import com.shop4e.shop.service.CartService;
import com.shop4e.shop.service.OrderService;
import com.shop4e.shop.util.UserUtil;
import com.shop4e.shop.util.mapper.ProductMapper;
import com.shop4e.shop.web.request.OrderRequest;
import com.shop4e.shop.web.response.OrderResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
  private final CartService cartService;
  private final ProductRepository productRepository;
  private final AddressRepository addressRepository;
  private final OrderRepository orderRepository;
  private final ProductMapper productMapper;
  private final UserUtil userUtil;

  public OrderServiceImpl(
      CartService cartService,
      ProductRepository productRepository,
      AddressRepository addressRepository,
      OrderRepository orderRepository,
      ProductMapper productMapper,
      UserUtil userUtil) {
    this.cartService = cartService;
    this.productRepository = productRepository;
    this.addressRepository = addressRepository;
    this.orderRepository = orderRepository;
    this.productMapper = productMapper;
    this.userUtil = userUtil;
  }

  @Override
  public List<OrderResponse> createOrder(List<OrderRequest> orders, Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    List<String> productIds = new ArrayList<>();
    List<Order> savedOrders = new ArrayList<>();

    for (OrderRequest orderRequest : orders) {
      productIds.add(orderRequest.getProductId());

      Product product = productRepository.findById(UUID.fromString(orderRequest.getProductId()))
          .orElseThrow(() -> new CustomException("Product not found."));
      Address address = addressRepository.findById(UUID.fromString(orderRequest.getAddressId()))
          .orElseThrow(() -> new CustomException("Address not found."));

      Order order = new Order();
      order.setOrderer(user);
      order.setAddress(address);
      order.setCurrency(product.getCurrency());
      order.setProduct(product);
      order.setPrice(product.getPrice());
      order.setQuantity(orderRequest.getQuantity());
      order.setStatus(OrderStatus.ACCEPTED);
      order.setSeller(product.getSeller());

      savedOrders.add(orderRepository.save(order));
    }

    productIds.forEach(productId -> {
      cartService.removeProduct(productId, principal);
    });

    return savedOrders.stream()
        .map(productMapper::mapOrderToOrderResponse)
        .collect(Collectors.toList());
  }

  @Override
  public List<OrderResponse> getOrders(Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    List<Order> orders = orderRepository.getOrdersByOrdererOrderByCreatedDesc(user);

    return orders.stream()
        .map(productMapper::mapOrderToOrderResponse)
        .collect(Collectors.toList());
  }

  @Override
  public List<OrderResponse> getSellerOrders(Authentication principal) {
    User user = userUtil.getUserFromPrincipal(principal);
    List<Order> orders = orderRepository.getOrdersBySellerOrderByCreatedDesc(user);

    return orders.stream()
        .map(productMapper::mapOrderToOrderResponse)
        .collect(Collectors.toList());
  }
}
