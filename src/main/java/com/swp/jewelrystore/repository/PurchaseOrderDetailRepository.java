package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.PurchaseOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetailEntity, Long> {
    void deleteByProductIn(List<ProductEntity> products);
}
