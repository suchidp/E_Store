package com.inventoryservice.controller.request;

import com.inventoryservice.model.Category;
import com.inventoryservice.model.SubCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class CategoryRequest {

    private String categoryName;
    private boolean isArchived;
    private List<SubCategory> subcategories;

    public static Category toCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setArchived(categoryRequest.isArchived());
        category.setSubcategories(categoryRequest.getSubcategories());
        return category;
    }
}
