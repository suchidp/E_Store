package com.inventoryservice.controller.request;

import com.inventoryservice.model.SubCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SubCategoryRequest {

    private String subCategoryName;
    private boolean isArchived;

    public static SubCategory toSubCategory(SubCategoryRequest subCategoryRequest) {
        SubCategory subCategory = new SubCategory();
        subCategory.setSubCategoryName(subCategoryRequest.getSubCategoryName());
        subCategory.setArchived(subCategoryRequest.isArchived());
        return subCategory;
    }
}
