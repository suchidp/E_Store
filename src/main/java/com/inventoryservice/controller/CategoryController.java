package com.inventoryservice.controller;

import com.inventoryservice.config.InventoryManagerAccess;
import com.inventoryservice.controller.request.*;
import com.inventoryservice.exception.*;
import com.inventoryservice.model.*;
import com.inventoryservice.service.CategoryService;
import com.inventoryservice.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SubCategoryService subcategoryService;

    public Category getCategory (Integer categoryId) throws CategoryNotFoundException {
        return categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    public SubCategory getSubCategory (Integer subCategoryId) throws SubCategoryNotFoundException {
        return subcategoryService.findSubCategoryById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory not found"));
    }

    /*  To Add new Category*/
    @PostMapping()
    @InventoryManagerAccess
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryRequest.toCategory(categoryRequest);
        return CategoryResponse.fromCategory(categoryService.createCategory(category));
    }

    /*  To find Category
     * @param  categoryId an Id giving the categoryId of specific Id
     * @return      Category of specific Id.
     * @throws CategoryNotFoundException  If an Category of specific Id is not found
     * */
    @GetMapping("/{categoryId}")
    public CategoryResponse findCategoryById(@PathVariable Integer categoryId) throws CategoryNotFoundException {
        Category category = getCategory(categoryId);
        return CategoryResponse.fromCategory(category);
    }

    /*
     * To get list of all Category
     * @return list of all Category .
     */
    @GetMapping()
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.stream()
                .map(CategoryResponse::fromCategory)
                .collect(Collectors.toList());
    }

    /*  To Update  Category */
    @PutMapping("/{categoryId}")
    @InventoryManagerAccess
    public CategoryResponse updateCategory(
            @PathVariable Integer categoryId,
            @RequestBody CategoryRequest categoryRequest
    ) throws CategoryNotFoundException, CategoryArchivedException {
        Category category = getCategory(categoryId);
        if (category.isArchived()) {
            throw new CategoryArchivedException("Category is archived and cannot be updated");
        }
        return CategoryResponse.fromCategory(categoryService.updateCategory(categoryId, categoryRequest));
    }

    /*  To Add new SubCategory */
    @PostMapping("/{categoryId}/subCategory")
    @InventoryManagerAccess
    public List<SubCategoryResponse> addSubCategories(
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody List<SubCategoryRequest> subCategoryRequests) {
        Category category = getCategory(categoryId);
        List<SubCategory> subCategories = subCategoryRequests.stream()
                .map(subCategoryRequest -> {
                    SubCategory subCategory = SubCategoryRequest.toSubCategory(subCategoryRequest);
                    subCategory.setCategory(category);
                    return subCategory;
                })
                .collect(Collectors.toList());
        List<SubCategory> savedSubCategories = subcategoryService.saveSubcategories(subCategories);
        return savedSubCategories.stream()
                .map(SubCategoryResponse::fromSubcategory)
                .collect(Collectors.toList());
    }

    /*
     * To get list of all SubCategory
     * @return      list of all SubCategory .
     */
    @GetMapping("/subCategory")
    public List<SubCategoryResponse> getAllSubCategory() {
        List<SubCategory> subCategories = subcategoryService.getAllSubCategory();
        return subCategories.stream()
                .map(SubCategoryResponse::fromSubcategory)
                .collect(Collectors.toList());
    }

    /*  To find subCategory
     * @param  subCategoryId an Id giving the subCategory of specific Id
     * @return      subCategory of specific Id.
     * @throws SubCategoryNotFoundException  If an subCategory of specific Id is not found
     * */
    @GetMapping("/subCategory/{subCategoryId}")
    public SubCategoryResponse getSubCategoryById(@PathVariable Integer subCategoryId) throws SubCategoryNotFoundException {
        SubCategory subCategory = getSubCategory(subCategoryId);
        return SubCategoryResponse.fromSubcategory(subCategory);
    }

    /*  To find subCategories of specific Category
     * @param  categoryId an Id giving the List of  subCategory .
     * @return      List<SubCategoryResponse> .
     * @throws CategoryNotFoundException  If an Category of specific Id is not found
     * */
    @GetMapping("/{categoryId}/subCategory")
    public List<SubCategoryResponse> getSubCategoriesByCategoryId(@PathVariable Integer categoryId) throws CategoryNotFoundException {
        Category category = getCategory(categoryId);
        List<SubCategory> subCategories = subcategoryService.getSubCategoryByCategoryId(category);
        return subCategories.stream()
                .map(SubCategoryResponse::fromSubcategory)
                .collect(Collectors.toList());
    }

    /*  To Update  SubCategory */
    @PutMapping("/subCategory/{subCategoryId}")
    @InventoryManagerAccess
    public SubCategoryResponse updateSubCategory(
            @PathVariable Integer subCategoryId, @RequestBody SubCategoryRequest subCategoryRequest
    ) throws SubCategoryNotFoundException, SubCategoryArchivedException {
        SubCategory subCategory = getSubCategory(subCategoryId);
        if (subCategory.isArchived()) {
            throw new SubCategoryArchivedException("SubCategory is archived and cannot be updated");
        }
        return SubCategoryResponse.fromSubcategory(subcategoryService.updateSubCategory(subCategoryId, subCategoryRequest));
    }

    /*  To Delete Category
     * @param  categoryId an Id giving the Category of specific Id
     * @return it deleted Category of specific Id.
     * @throws CategoryNotFoundException  If an Category of specific Id is not found
     */
    @DeleteMapping("/{categoryId}")
    @InventoryManagerAccess
    public void deleteCategory(@PathVariable Integer categoryId) throws CategoryNotArchivedException, CategoryNotFoundException {
        Category category = getCategory(categoryId);
        if (!category.isArchived()) {
            throw new CategoryNotArchivedException("Category is not archived and cannot be deleted");
        }
        categoryService.deleteCategory(categoryId);
    }

    /*  To Delete SubCategory
     * @param  subCategoryId an Id giving the Category of specific Id
     * @return it deleted SubCategory of specific Id.
     * @throws SubCategoryNotFoundException  If an SubCategory of specific Id is not found
     */
    @DeleteMapping("/subCategory/{subCategoryId}")
    @InventoryManagerAccess
    public void deleteSubCategory(@PathVariable Integer subCategoryId) throws SubCategoryNotArchivedException, SubCategoryNotFoundException {
        SubCategory subCategory = getSubCategory(subCategoryId);
        if (!subCategory.isArchived()) {
            throw new SubCategoryNotArchivedException("subCategory is  not archived and cannot be deleted");
        }
        subcategoryService.deleteSubCategory(subCategoryId);
    }

    @PostMapping("/{categoryId}/archiveOrUnarchive-category")
    @InventoryManagerAccess
    public CategoryResponse archiveOrUnarchiveCategory(@PathVariable Integer categoryId, @RequestBody boolean isArchived) {
        Category category = getCategory(categoryId);
        if (isArchived && category.isArchived()) {
            throw new CategoryArchivedException("Category is already archived");
        } else if (!isArchived && !category.isArchived()) {
            throw new CategoryArchivedException("Category is already unarchived");
        }
        if (isArchived) {
            category = categoryService.archiveCategory(categoryId);
        } else {
            category = categoryService.unarchiveCategory(categoryId);
        }
        return CategoryResponse.fromCategory(category);
    }

    @PostMapping("/subCategory/{subCategoryId}/archiveOrUnarchive-subcategory")
    @InventoryManagerAccess
    public SubCategoryResponse archiveOrUnarchiveSubCategory(@PathVariable Integer subCategoryId, @RequestBody boolean isArchived) {
        SubCategory subCategory = getSubCategory(subCategoryId);
        if (isArchived && subCategory.isArchived()) {
            throw new SubCategoryArchivedException("SubCategory is already archived");
        } else if (!isArchived && !subCategory.isArchived()) {
            throw new SubCategoryArchivedException("SubCategory is already unarchived");
        }
        if (isArchived) {
            subCategory = subcategoryService.archiveSubCategory(subCategoryId);
        } else {
            subCategory = subcategoryService.unArchiveSubCategory(subCategoryId);
        }
        return SubCategoryResponse.fromSubcategory(subCategory);
    }
}
