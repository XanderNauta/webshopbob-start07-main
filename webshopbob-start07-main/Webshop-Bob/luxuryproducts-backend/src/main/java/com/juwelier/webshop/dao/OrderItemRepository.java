package com.juwelier.webshop.dao;

import com.juwelier.webshop.models.Order;
import com.juwelier.webshop.models.OrderItem;
import com.juwelier.webshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    OrderItem findByProductAndOrder(Product product, Order order);

    OrderItem findByProductAndOrderAndExplanationAndReason(Product product, Order order,
                                                           String explanation, String reason);

    List<OrderItem> findAllByStatus(String status);

}
