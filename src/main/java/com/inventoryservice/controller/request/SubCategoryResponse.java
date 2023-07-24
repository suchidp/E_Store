package com.inventoryservice.controller.request;

import com.inventoryservice.model.SubCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class SubCategoryResponse {

    private Integer subCategoryId;
    private String subCategoryName;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
    private boolean isArchived;

    public static SubCategoryResponse fromSubcategory(SubCategory subCategory) {
        SubCategoryResponse subCategoryResponse = new SubCategoryResponse();
        subCategoryResponse.setSubCategoryId(subCategory.getSubCategoryId());
        subCategoryResponse.setSubCategoryName(subCategory.getSubCategoryName());
        subCategoryResponse.setCreatedOn(subCategory.getCreatedOn());
        subCategoryResponse.setLastUpdatedOn(subCategory.getLastUpdatedOn());
        subCategoryResponse.setArchived(subCategory.isArchived());
        return subCategoryResponse;
    }
}
