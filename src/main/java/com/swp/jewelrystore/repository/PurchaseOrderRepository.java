package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.PurchaseOrderRepositoryCustom;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository <PurchaseOrderEntity, Long>, PurchaseOrderRepositoryCustom {
    PurchaseOrderEntity findByPurchaseOrderCode(String code);
    List<PurchaseOrderEntity> findPurchaseOrderEntitiesByStatusIsIn(List<String> statusList);
    List<PurchaseOrderEntity> findByUserIdAndStatusNot(Long customerId,String status);
}   
