package com.juwelier.webshop.dto;

import com.juwelier.webshop.models.CartItem;
import com.juwelier.webshop.models.Product;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class OrderDTO {
    public List<CartItem> cartItems;
    public double totalPrice;

    public String orderDate;

//    public OrderDTO(List<Product> products, double totalPrice, String orderDate) {
//        this.products = products;
//        this.totalPrice = totalPrice;
//        this.orderDate = orderDate;
//    }


}
