package com.juwelier.webshop.dao;

import com.juwelier.webshop.models.Customer;
import com.juwelier.webshop.models.Order;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByCustomer (Customer customer);
}
