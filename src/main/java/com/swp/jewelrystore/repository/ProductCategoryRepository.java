package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.ProductCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {
    ProductCategoryEntity findByCategoryName(String categoryName);
}
