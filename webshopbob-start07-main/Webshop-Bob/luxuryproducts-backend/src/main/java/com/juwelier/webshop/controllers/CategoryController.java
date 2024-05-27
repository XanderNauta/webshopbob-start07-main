package com.juwelier.webshop.controllers;

import com.juwelier.webshop.dao.CategoryDAO;
import com.juwelier.webshop.dto.CategoryDTO;
import com.juwelier.webshop.models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://s1130782.student.inf-hsleiden.nl:10782"})
@RequestMapping("/categories")
public class CategoryController {
    private CategoryDAO categoryDAO;

    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }


    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(this.categoryDAO.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable long categoryId) {
        return ResponseEntity.ok(this.categoryDAO.getCategoryById(categoryId));
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody CategoryDTO categoryDTO){
        this.categoryDAO.createCategory(categoryDTO);
        return ResponseEntity.ok("Category was successfully created.");
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<String> updateProduct(@PathVariable long categoryId, @RequestBody CategoryDTO categoryDTO){
        this.categoryDAO.updateCategoryById(categoryId, categoryDTO);
        return ResponseEntity.ok("Category was successfully updated.");
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long categoryId){
        this.categoryDAO.deleteCategoryById(categoryId);
        return ResponseEntity.ok("Category was successfully deleted.");
    }
}