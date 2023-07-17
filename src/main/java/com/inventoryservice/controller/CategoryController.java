package com.inventoryservice.controller;

import com.inventoryservice.config.InventoryManagerAccess;
import com.inventoryservice.controller.request.*;
import com.inventoryservice.exception.*;
import com.inventoryservice.model.*;
import com.inventoryservice.service.CategoryService;
import com.inventoryservice.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /*  To Add new Category*/
    @PostMapping()
    @InventoryManagerAccess
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryRequest.toCategory(categoryRequest);
        categoryService.createCategory(category);
        CategoryResponse categoryResponse = CategoryResponse.fromCategory(category);
        return new ResponseEntity<>(categoryResponse, HttpStatus.FOUND);
    }

    /*  To find Category
     * @param  categoryId an Id giving the categoryId of specific Id
     * @return      Category of specific Id.
     * @throws CategoryNotFoundException  If an Category of specific Id is not found
     * */
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> findCategoryById(@PathVariable Integer categoryId) throws CategoryNotFoundException {
        Category category = categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        CategoryResponse categoryResponse = CategoryResponse.fromCategory(category);
        return new ResponseEntity<>(categoryResponse, HttpStatus.FOUND);
    }

    /*
     * To get list of all Category
     * @return list of all Category .
     */
    @GetMapping()
    public ResponseEntity<?> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> categoryResponses = categories.stream()
                .map(CategoryResponse::fromCategory)
                .collect(Collectors.toList());
        return new ResponseEntity<>(categoryResponses, HttpStatus.OK);
    }

    /*  To Update  Category */
    @PutMapping("/{categoryId}")
    @InventoryManagerAccess
    public ResponseEntity<?> updateCategory(
            @PathVariable Integer categoryId,
            @RequestBody CategoryRequest categoryRequest
    ) throws CategoryNotFoundException, CategoryArchivedException {
        Category category = categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        if (category.isArchived()) {
            throw new CategoryArchivedException("Category is archived and cannot be updated");
        }
        CategoryResponse categoryResponse = CategoryResponse.fromCategory(categoryService.updateCategory(categoryId, categoryRequest));
        return new ResponseEntity<>(categoryResponse, HttpStatus.FOUND);
    }

    /*  To Add new SubCategory */
    @PostMapping("/{categoryId}/subCategory")
    @InventoryManagerAccess
    public ResponseEntity<?> addSubCategories(
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody List<SubCategoryRequest> subCategoryRequests) {
        Category category = categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        List<SubCategory> subCategories = subCategoryRequests.stream()
                .map(subCategoryRequest -> {
                    SubCategory subCategory = SubCategoryRequest.toSubCategory(subCategoryRequest);
                    subCategory.setCategory(category);
                    System.out.println(subCategory);
                    return subCategory;
                })
                .collect(Collectors.toList());
        List<SubCategory> savedSubCategories = subcategoryService.saveSubcategories(subCategories);
        List<SubCategoryResponse> subCategoryResponses = savedSubCategories.stream()
                .map(SubCategoryResponse::fromSubcategory)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(subCategoryResponses);
    }

    /*
     * To get list of all SubCategory
     * @return      list of all SubCategory .
     */
    @GetMapping(("/subCategory"))
    public ResponseEntity<?> getAllSubCategory() {
        List<SubCategory> subCategories = subcategoryService.getAllSubCategory();
        List<SubCategoryResponse> subCategoryResponse = subCategories.stream()
                .map(SubCategoryResponse::fromSubcategory)
                .collect(Collectors.toList());
        return new ResponseEntity<>(subCategoryResponse, HttpStatus.OK);

    }

    /*  To find subCategory
     * @param  subCategoryId an Id giving the subCategory of specific Id
     * @return      subCategory of specific Id.
     * @throws SubCategoryNotFoundException  If an subCategory of specific Id is not found
     * */
    @GetMapping("/subCategory/{subCategoryId}")
    public ResponseEntity<?> getSubCategoryById(@PathVariable Integer subCategoryId) throws SubCategoryNotFoundException {
        SubCategory subCategory = subcategoryService.findSubCategoryById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory not found"));
        SubCategoryResponse subCategoryResponse = SubCategoryResponse.fromSubcategory(subCategory);
        return new ResponseEntity<>(subCategoryResponse, HttpStatus.FOUND);
    }

    /*  To find subCategories of specific Category
     * @param  categoryId an Id giving the List of  subCategory .
     * @return      List<SubCategoryResponse> .
     * @throws CategoryNotFoundException  If an Category of specific Id is not found
     * */
    @GetMapping("/{categoryId}/subCategory")
    public ResponseEntity<?> getSubCategoriesByCategoryId(@PathVariable Integer categoryId) throws CategoryNotFoundException {
        Category category = categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        List<SubCategory> subCategories = subcategoryService.getSubCategoryByCategoryId(category);
        List<SubCategoryResponse> subCategoryResponse = subCategories.stream()
                .map(SubCategoryResponse::fromSubcategory)
                .collect(Collectors.toList());
        return new ResponseEntity<>(subCategoryResponse, HttpStatus.OK);
    }

    /*  To Update  SubCategory */
    @PutMapping("/subCategory/{subCategoryId}")
    @InventoryManagerAccess
    public ResponseEntity<?> updateSubCategory(
            @PathVariable Integer subCategoryId, @RequestBody SubCategoryRequest subCategoryRequest
    ) throws SubCategoryNotFoundException, SubCategoryArchivedException {
        SubCategory subCategory = subcategoryService.findSubCategoryById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory not found"));
        if (subCategory.isArchived()) {
            throw new SubCategoryArchivedException("subCategory is archived and cannot be updated");
        }
        SubCategoryResponse subCategoryResponse = SubCategoryResponse.fromSubcategory(subcategoryService.updateSubCategory(subCategoryId, subCategoryRequest));
        return new ResponseEntity<>(subCategoryResponse, HttpStatus.FOUND);
    }

    /*  To Delete Category
     * @param  categoryId an Id giving the Category of specific Id
     * @return it deleted Category of specific Id.
     * @throws CategoryNotFoundException  If an Category of specific Id is not found
     */
    @DeleteMapping("/{categoryId}")
    @InventoryManagerAccess
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId) throws CategoryNotArchivedException, CategoryNotFoundException {
        Category category = categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        if (!category.isArchived()) {
            throw new CategoryNotArchivedException("Category is  not archived and cannot be deleted");
        }
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.FOUND);
    }

    /*  To Delete SubCategory
     * @param  subCategoryId an Id giving the Category of specific Id
     * @return it deleted SubCategory of specific Id.
     * @throws SubCategoryNotFoundException  If an SubCategory of specific Id is not found
     */
    @DeleteMapping("/subCategory/{subCategoryId}")
    @InventoryManagerAccess
    public ResponseEntity<?> deleteSubCategory(@PathVariable Integer subCategoryId) throws SubCategoryNotArchivedException, SubCategoryNotFoundException {
        SubCategory subCategory = subcategoryService.findSubCategoryById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory not found"));
        if (!subCategory.isArchived()) {
            throw new SubCategoryNotArchivedException("subCategory is  not archived and cannot be deleted");
        }
        subcategoryService.deleteSubCategory(subCategoryId);
        return new ResponseEntity<>("subCategory deleted successfully", HttpStatus.FOUND);
    }

    @PostMapping("/{categoryId}/archiveOrUnarchive-category")
    @InventoryManagerAccess
    public ResponseEntity<Category> archiveOrUnarchiveCategory(@PathVariable Integer categoryId, @RequestBody boolean isArchived) {
        Category category = categoryService.findCategoryById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        if (isArchived) {
            if (!category.isArchived()) {
                category = categoryService.archiveCategory(categoryId);
            } else {
                throw new CategoryArchivedException("Category is already archived");
            }
        } else {
            if (category.isArchived()) {
                category = categoryService.unarchiveCategory(categoryId);
            } else {
                throw new CategoryArchivedException("Category is already unarchived");
            }
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/subCategory/{subCategoryId}/archiveOrUnarchive-subcategory")
    @InventoryManagerAccess
    public ResponseEntity<SubCategory> archiveOrUnarchiveSubCategory(@PathVariable Integer subCategoryId, @RequestBody boolean isArchived) {
        SubCategory subCategory = subcategoryService.findSubCategoryById(subCategoryId)
                .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory not found"));

        if (isArchived) {
            if (!subCategory.isArchived()) {
                subCategory = subcategoryService.archiveSubCategory(subCategoryId);
            } else {
                throw new SubCategoryArchivedException("SubCategory is already archived");
            }
        } else {
            if (subCategory.isArchived()) {
                subCategory = subcategoryService.unArchiveSubCategory(subCategoryId);
            } else {
                throw new SubCategoryArchivedException("SubCategory is already unarchived");
            }
        }
        return new ResponseEntity<>(subCategory, HttpStatus.OK);
    }
}
