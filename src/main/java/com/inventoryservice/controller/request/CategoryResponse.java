package com.inventoryservice.controller.request;

import com.inventoryservice.model.Category;
import com.inventoryservice.model.SubCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CategoryResponse {
    private Integer categoryId;
    private String categoryName;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdatedOn;
    private boolean isArchived;
    private List<SubCategory> subcategories;

    public static CategoryResponse fromCategory(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(category.getCategoryId());
        categoryResponse.setCategoryName(category.getCategoryName());
        categoryResponse.setCreatedOn(category.getCreatedOn());
        categoryResponse.setLastUpdatedOn(category.getLastUpdatedOn());
        categoryResponse.setArchived(category.isArchived());
        categoryResponse.setSubcategories(category.getSubcategories());
        return categoryResponse;
    }
}
