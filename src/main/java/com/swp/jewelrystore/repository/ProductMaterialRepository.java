package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.ProductGemEntity;
import com.swp.jewelrystore.entity.ProductMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterialEntity, Long> {
    List<ProductMaterialEntity> findAllByProductId(Long productId);
}
