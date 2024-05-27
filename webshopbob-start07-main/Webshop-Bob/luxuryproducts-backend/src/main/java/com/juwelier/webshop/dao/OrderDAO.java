package com.juwelier.webshop.dao;

import com.juwelier.webshop.dto.OrderDTO;
import com.juwelier.webshop.models.*;
import com.juwelier.webshop.services.CustomerService;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class OrderDAO {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private LocalDate localDate = LocalDate.now();

    private ProductRepository productRepository;

    private OrderItemRepository orderItemRepository;


    public OrderDAO(OrderRepository orderRepository, CustomerRepository customerRepository, CustomerService customerService,
                    ProductRepository productRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<Order> getOrdersByCustomer() {
        Customer customer = customerService.getActiveUser();
        return this.orderRepository.findAllByCustomer(customer);
    }

    public Optional<Order> getOrder(UUID id){
        return this.orderRepository.findById(id);
    }

    public List<Order> getAllRetunableOrders() {
        List<Order> returnableOrders = new ArrayList<Order>();
        Customer customer = customerService.getActiveUser();
        List<Order> returnOrder = this.orderRepository.findAllByCustomer(customer);

        for (Order order : returnOrder) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM dd ");
            LocalDate date = LocalDate.now();

            LocalDate dateOrder = LocalDate.parse(order.getOrderDate()
                    .replace("/", "-"));


            long daysbetween = ChronoUnit.DAYS.between(dateOrder, date);

            if (daysbetween <= 30) {
                returnableOrders.add(order);
            }
        }

        return returnableOrders;
    }

    public void placeOrder(OrderDTO orderDTO) {
        Customer customer = customerService.getActiveUser();
        Order order = new Order(customer, orderDTO.totalPrice, orderDTO.orderDate);
        this.orderRepository.save(order);

        for (CartItem cartItem: orderDTO.cartItems) {
            Optional<Product> productOptional = this.productRepository.findById(cartItem.getId());

            if (productOptional.isPresent()) {
                Product productSave = productOptional.get();
                double stockUpdate = productSave.getStock();
                stockUpdate -= 1;
                productSave.setStock(stockUpdate);

                OrderItem orderItem = new OrderItem(order, productSave);
                this.orderItemRepository.save(orderItem);
                this.productRepository.save(productSave);

            }
        }

//        Order newOrder = new Order(
//                customer,
//                orderDTO.totalPrice,
//                orderDTO.orderDate = localDate.toString()
//        );
//        this.orderRepository.save(newOrder);
    }

    public void updateOrderById(UUID orderId, OrderDTO orderDTO) {
        Optional<Order> optionalOrder = this.orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order updatedOrder = optionalOrder.get();
           // updatedOrder.setProducts(orderDTO.products);
            updatedOrder.setTotalPrice(orderDTO.totalPrice);
            this.orderRepository.save(updatedOrder);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to update order: Order with ID: '" + orderId + "' does not exist.");
        }
    }

    public void completeOrder(UUID orderId) {
        Optional<Order> optionalOrder = this.orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order completedOrder = optionalOrder.get();
            completedOrder.setOrderStatus("Completed");
            this.orderRepository.save(completedOrder);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to complete order: Order with ID: '" + orderId + "'does not exist.");
        }
    }

    public void deleteOrderById(UUID orderId) {
        Optional<Order> optionalOrder = this.orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            this.orderRepository.delete(optionalOrder.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to delete order: Order with ID: '" + orderId + "' does not exist.");
        }
    }
}
