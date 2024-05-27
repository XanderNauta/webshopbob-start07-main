package com.juwelier.webshop.dao;

import com.juwelier.webshop.dto.ReturnDTO;
import com.juwelier.webshop.models.Order;
import com.juwelier.webshop.models.OrderItem;
import com.juwelier.webshop.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class RetourDAO {

    private OrderItemRepository orderItemRepository;

    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    public RetourDAO(OrderItemRepository orderItemRepository,
                     ProductRepository productRepository, OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public void returnItem(ReturnDTO returnDTO) {
        Product product = this.productRepository.findProductById(returnDTO.productId);
        Optional<Order> optionalOrder = this.orderRepository.findById(returnDTO.orderId);
        String reason = returnDTO.reason;
        String explanation = returnDTO.explanation;

        if (optionalOrder.isPresent()) {
            Order updateOrder = optionalOrder.get();
            OrderItem orderItem = this.orderItemRepository.findByProductAndOrder(
                    product, updateOrder);
                orderItem.setStatus("Returning...");
                orderItem.setReason(reason);
                orderItem.setExplanation(explanation);
                this.orderItemRepository.save(orderItem);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "item not found");
        }
    }

    public List<OrderItem> getAllRequests(){

        return this.orderItemRepository.findAllByStatus("Returning...");
    }

    public void acceptToStock(ReturnDTO returnDTO){
        Product product = this.productRepository.findProductById(returnDTO.productId);
        double stock = product.getStock();
        Optional<Order> optionalOrder = this.orderRepository.findById(returnDTO.orderId);
        String reason = returnDTO.reason;
        String explanation = returnDTO.explanation;
        if (optionalOrder.isPresent()) {
            Order updateOrder = optionalOrder.get();
            OrderItem orderItem = this.orderItemRepository.findByProductAndOrder(
                    product, updateOrder);
            stock += 1;
            double totalprice = updateOrder.getTotalPrice() - product.getPrice();
            orderItem.setStatus("Returned");
            this.orderItemRepository.save(orderItem);
            product.setStock(stock);
            this.productRepository.save(product);
            updateOrder.setTotalPrice(totalprice);
            this.orderRepository.save(updateOrder);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed in returning");
        }


    }

    public void acceptReturn(ReturnDTO returnDTO){
        Product product = this.productRepository.findProductById(returnDTO.productId);
        Optional<Order> optionalOrder = this.orderRepository.findById(returnDTO.orderId);
        String reason = returnDTO.reason;
        String explanation = returnDTO.explanation;
        if (optionalOrder.isPresent()) {
            Order updateOrder = optionalOrder.get();
            OrderItem orderItem = this.orderItemRepository.findByProductAndOrder(
                    product, updateOrder);
            orderItem.setStatus("Returned");
            double totalprice = updateOrder.getTotalPrice() - product.getPrice();
            this.orderItemRepository.save(orderItem);
            updateOrder.setTotalPrice(totalprice);
            this.orderRepository.save(updateOrder);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed in returning");
        }


    }

    public void declineReturn(ReturnDTO returnDTO){
        Product product = this.productRepository.findProductById(returnDTO.productId);
        Optional<Order> optionalOrder = this.orderRepository.findById(returnDTO.orderId);
        if (optionalOrder.isPresent()) {
            Order updateOrder = optionalOrder.get();
            OrderItem orderItem = this.orderItemRepository.findByProductAndOrder(
                    product, updateOrder);
            orderItem.setStatus("Return rejected");
            this.orderItemRepository.save(orderItem);
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed in returning");
        }
    }
}
