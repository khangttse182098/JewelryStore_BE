package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.PurchaseOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetailEntity, Long> {
}
