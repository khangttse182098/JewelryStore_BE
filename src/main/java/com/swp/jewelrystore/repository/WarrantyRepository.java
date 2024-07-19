package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.WarrantyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WarrantyRepository extends JpaRepository<WarrantyEntity, Long> {
    WarrantyEntity findByProduct(ProductEntity product);
}
