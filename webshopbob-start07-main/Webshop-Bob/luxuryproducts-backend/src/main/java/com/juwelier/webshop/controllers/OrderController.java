package com.juwelier.webshop.controllers;

import com.juwelier.webshop.dao.OrderDAO;
import com.juwelier.webshop.dto.OrderDTO;
import com.juwelier.webshop.models.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://s1130782.student.inf-hsleiden.nl:10782"})
@RequestMapping("/orders")
public class OrderController {
    private OrderDAO orderDAO;

    public OrderController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {

        return ResponseEntity.ok(this.orderDAO.getOrdersByCustomer());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Optional<Order>> getProduct(@PathVariable UUID orderId) {
        return ResponseEntity.ok(this.orderDAO.getOrder(orderId));
    }

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) {
        this.orderDAO.placeOrder(orderDTO);
        return ResponseEntity.ok("Order was successfully placed");
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable UUID orderId, @RequestBody OrderDTO orderDTO) {
        this.orderDAO.updateOrderById(orderId, orderDTO);
        return ResponseEntity.ok("Order was successfully updated.");
    }

    @PutMapping("/complete/{orderId}")
    public ResponseEntity<String> completeOrder(@PathVariable UUID orderId) {
        this.orderDAO.completeOrder(orderId);
        return ResponseEntity.ok("Order was successfully completed.");
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID orderId) {
        this.orderDAO.deleteOrderById(orderId);
        return ResponseEntity.ok("Order was successfully deleted");
    }
}
