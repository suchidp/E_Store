package com.inventoryservice.service;

import com.inventoryservice.controller.request.SubCategoryRequest;
import com.inventoryservice.model.Category;
import com.inventoryservice.model.SubCategory;
import com.inventoryservice.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    @Autowired
    private SubCategoryRepository subcategoryRepository;

    public List<SubCategory> getAllSubCategory() {
        return subcategoryRepository.findAll();
    }

    public List<SubCategory> saveSubcategories(List<SubCategory> subCategories) {
        for (SubCategory subCategory : subCategories) {
            subCategory.setCreatedOn(LocalDateTime.now());
            subCategory.setLastUpdatedOn(LocalDateTime.now());
        }
        return subcategoryRepository.saveAll(subCategories);
    }

    public List<SubCategory> getSubCategoryByCategoryId(Category category) {
        return subcategoryRepository.findByCategory(category);
    }

    public Optional<SubCategory> findSubCategoryById(Integer subCategoryId) {
        return subcategoryRepository.findById(subCategoryId);
    }

    public SubCategory updateSubCategory(Integer subCategoryId, SubCategoryRequest subCategoryRequest) {
        Optional<SubCategory> existingSubCategory = subcategoryRepository.findById(subCategoryId);
        SubCategory subCategory = existingSubCategory.get();
        subCategory.setSubCategoryName(subCategoryRequest.getSubCategoryName());
        subCategory.setLastUpdatedOn(LocalDateTime.now());
        return subcategoryRepository.save(subCategory);
    }

    public void deleteSubCategory(Integer subCategoryId) {
        subcategoryRepository.deleteById(subCategoryId);
    }

    public SubCategory archiveSubCategory(Integer subCategoryId) {
        SubCategory subCategory = subcategoryRepository.findById(subCategoryId).get();
        subCategory.setArchived(true);
        return subcategoryRepository.save(subCategory);
    }

    public SubCategory unArchiveSubCategory(Integer subCategoryId) {
        SubCategory subCategory = subcategoryRepository.findById(subCategoryId).get();
        subCategory.setArchived(false);
        return subcategoryRepository.save(subCategory);
    }
}
