package com.inventoryservice.service;

import com.inventoryservice.controller.request.CategoryRequest;
import com.inventoryservice.model.Category;
import com.inventoryservice.model.SubCategory;
import com.inventoryservice.repository.CategoryRepository;
import com.inventoryservice.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subcategoryRepository;

    /*The method used to create the Category*/
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    /*The method used to list of  the Category*/
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /*
     *The method used to  get the Category by Using  categoryId.*/
    public Optional<Category> findCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Category updateCategory(Integer categoryId, CategoryRequest categoryRequest) {
        Optional<Category> categories = categoryRepository.findById(categoryId);
        Category newCategory = categories.get();
        newCategory.setCategoryName(categoryRequest.getCategoryName());
        newCategory.setLastUpdatedOn(LocalDateTime.now());
        return categoryRepository.save(newCategory);
    }

    /*
   The method used to  delete the Category.
   This method check Category is present or not   .
   */
    public void deleteCategory(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Category archiveCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        category.setArchived(true);
        for (SubCategory subCategory : category.getSubcategories()) {
            subCategory.setArchived(true);
            subcategoryRepository.save(subCategory);
        }
        categoryRepository.save(category);
        return category;
    }

    public Category unarchiveCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        category.setArchived(false);
        for (SubCategory subCategory : category.getSubcategories()) {
            subCategory.setArchived(false);
            subcategoryRepository.save(subCategory);
        }
        categoryRepository.save(category);
        return category;
    }
}
