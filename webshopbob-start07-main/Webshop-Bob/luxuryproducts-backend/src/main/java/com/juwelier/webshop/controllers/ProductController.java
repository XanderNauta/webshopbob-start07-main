package com.juwelier.webshop.controllers;

import com.juwelier.webshop.dao.ProductDAO;
import com.juwelier.webshop.dto.ProductDTO;
import com.juwelier.webshop.models.Product;
import com.juwelier.webshop.utils.Seeder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://s1130782.student.inf-hsleiden.nl:10782"})
@RequestMapping("/products")
public class ProductController {
    private ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(this.productDAO.getAllProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable long productId) {
        return ResponseEntity.ok(this.productDAO.getProductById(productId));
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO){
        this.productDAO.createProduct(productDTO);
        return ResponseEntity.ok("Product was successfully created.");
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable long productId, @RequestBody ProductDTO productDTO){
        this.productDAO.updateProductById(productId, productDTO);
        return ResponseEntity.ok("Product was successfully updated.");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable long productId){
        this.productDAO.deleteProductById(productId);
        return ResponseEntity.ok("Product was successfully deleted.");
    }
}
