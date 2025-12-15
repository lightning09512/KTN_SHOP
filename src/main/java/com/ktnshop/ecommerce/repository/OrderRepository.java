package com.ktnshop.ecommerce.repository;

import com.ktnshop.ecommerce.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    List<Order> findByCustomer_Id(Long customerId);
    List<Order> findByStatus(Order.OrderStatus status);
}
