package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.PurchaseOrderRepositoryCustom;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository <PurchaseOrderEntity, Long>, PurchaseOrderRepositoryCustom {

}
