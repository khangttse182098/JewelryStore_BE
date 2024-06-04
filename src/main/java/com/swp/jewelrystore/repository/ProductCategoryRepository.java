package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    ProductCategoryEntity findByCategoryNameAndSubCategoryType(String categoryName, String subCategoryType);
    ProductCategoryEntity findByCategoryName(String categoryName);
}
