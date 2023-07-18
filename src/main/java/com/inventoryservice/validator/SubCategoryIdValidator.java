package com.inventoryservice.validator;

import com.inventoryservice.exception.SubCategoryNotFoundException;
import com.inventoryservice.model.SubCategory;
import com.inventoryservice.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryIdValidator {

    @Autowired
    private SubCategoryService subcategoryService;

    public SubCategory findSubCategoryById(Integer subCategoryId) throws SubCategoryNotFoundException {
        return subcategoryService.findSubCategoryById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory not found"));
    }
}
