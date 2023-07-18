package com.inventoryservice.validator;

import com.inventoryservice.exception.CategoryNotFoundException;
import com.inventoryservice.model.Category;
import com.inventoryservice.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryIdValidator {

    @Autowired
    private CategoryService categoryService;

    public Category findCategoryById(Integer categoryId) throws CategoryNotFoundException {
        return categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }
}