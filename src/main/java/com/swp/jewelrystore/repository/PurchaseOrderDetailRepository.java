package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.PurchaseOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PurchaseOrderDetailRepository extends JpaRepository<PurchaseOrderDetailEntity, Long> {

}
