package com.inventoryservice.repository;

import com.inventoryservice.model.Category;
import com.inventoryservice.model.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
     List<SubCategory> findByCategory(Category category);
}
