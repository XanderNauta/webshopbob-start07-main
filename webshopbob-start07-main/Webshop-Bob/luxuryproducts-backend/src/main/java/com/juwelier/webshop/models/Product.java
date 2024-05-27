package com.juwelier.webshop.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "product")
public class Product {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String imagePath;
    private String description;
    private double price;

    private double stock;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JsonBackReference
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<OrderItem> orderItem;


    public Product() {}

    public Product(String name, String imagePath, String description,
                   double price, double stock, Category category) {
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
}
