package com.juwelier.webshop.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProductDTO {

    public String name;
    public String imagePath;
    public String description;
    public double price;
    @JsonAlias("category_id")
    public long categoryId;

    public ProductDTO(String name, String imagePath, String description, double price, long categoryId) {
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }
}
