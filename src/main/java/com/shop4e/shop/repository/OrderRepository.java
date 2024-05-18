package com.shop4e.shop.repository;

import com.shop4e.shop.domain.Order;
import com.shop4e.shop.domain.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
  List<Order> getOrdersByOrdererOrderByCreatedDesc(User orderer);

  List<Order> getOrdersBySellerOrderByCreatedDesc(User seller);
}
