package com.juwelier.webshop.dao;

import com.juwelier.webshop.dto.CategoryDTO;
import com.juwelier.webshop.models.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryDAO {
    private CategoryRepository categoryRepository;

    public CategoryDAO(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> getAllCategories(){
        return this.categoryRepository.findAll();
    }

    public Category getCategoryById(long categoryId) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to get product: Category with ID '" + categoryId + "' does not exist.");
        }
    }

    public void createCategory(CategoryDTO categoryDTO){
        Category newCategory = new Category(categoryDTO.name);
        this.categoryRepository.save(newCategory);
    }

    public void updateCategoryById(long categoryId, CategoryDTO categoryDTO){
        Optional<Category> categoryOptional = this.categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()){
            Category updatedCategory = categoryOptional.get();
            updatedCategory.setName(categoryDTO.name);
            this.categoryRepository.save(updatedCategory);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to update category: Category with ID '" + categoryId + "' does not exist.");
        }
    }

    public void deleteCategoryById(long categoryId){
        Optional<Category> categoryOptional = this.categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent()){
            Category deletedCategory = categoryOptional.get();
            this.categoryRepository.delete(deletedCategory);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Failed to delete category: Category with ID '" + categoryId + "' does not exist.");
        }
    }
}
