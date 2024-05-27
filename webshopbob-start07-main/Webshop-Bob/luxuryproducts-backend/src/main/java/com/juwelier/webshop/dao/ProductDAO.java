package com.juwelier.webshop.dao;

import com.juwelier.webshop.dto.ProductDTO;
import com.juwelier.webshop.models.Category;
import com.juwelier.webshop.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class ProductDAO {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public ProductDAO(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public List<Product> getAllProducts(){
        return this.productRepository.findAll();
    }

    public Product getProductById(long productId) {
        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to get product: Product with ID '" + productId + "' does not exist.");
        }
    }

    public void createProduct(ProductDTO productDTO){
        Optional<Category> categoryOptional = this.categoryRepository.findById(productDTO.categoryId);
        if (categoryOptional.isPresent()){
            Category category = categoryOptional.get();
            Product newProduct = new Product(
                    productDTO.name,
                    productDTO.imagePath,
                    productDTO.description,
                    productDTO.price,
                    2,
                    category

            );
            this.productRepository.save(newProduct);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to create product: Category with ID '" + productDTO.categoryId + "' does not exist.");
        }
    }

    public void updateProductById(long productId, ProductDTO productDTO){
        Optional<Product> productOptional = this.productRepository.findById(productId);
        Optional<Category> categoryOptional = this.categoryRepository.findById(productDTO.categoryId);
        if (productOptional.isPresent()){
            Product updatedProduct = productOptional.get();
            if (categoryOptional.isPresent()){
                Category category = categoryOptional.get();
                updatedProduct.setName(productDTO.name);
                updatedProduct.setImagePath(productDTO.imagePath);
                updatedProduct.setDescription(productDTO.description);
                updatedProduct.setPrice(productDTO.price);
                updatedProduct.setCategory(category);
                this.productRepository.save(updatedProduct);
            } else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Failed to update product: Category with ID '" + productDTO.categoryId + "' does not exist.");
            }
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to update product: Product with ID '" + productId + "' does not exist.");
        }
    }

    public void deleteProductById(long productId){
        Optional<Product> productOptional = this.productRepository.findById(productId);
        if (productOptional.isPresent()){
            Product deletedProduct = productOptional.get();
            this.productRepository.delete(deletedProduct);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to delete product: Product with ID '" + productId + "' does not exist.");
        }
    }
}
