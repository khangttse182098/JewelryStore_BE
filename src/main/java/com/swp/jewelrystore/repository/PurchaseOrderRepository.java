package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.PurchaseOrderRepositoryCustom;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseOrderRepository extends JpaRepository <PurchaseOrderEntity, Long>, PurchaseOrderRepositoryCustom, CrudRepository<PurchaseOrderEntity, Long> {
    PurchaseOrderEntity findByPurchaseOrderCode(String code);
    List<PurchaseOrderEntity> findPurchaseOrderEntitiesByStatusIsIn(List<String> statusList);
    List<PurchaseOrderEntity> findByUserIdAndStatusNot(Long customerId,String status);
    @Query(value = "SELECT * FROM purchaseorder WHERE MONTH(created_date) = :month AND YEAR(created_date) = :year", nativeQuery = true)
    List<PurchaseOrderEntity> findByCreatedDateMonth(@Param("month") int month, @Param("year") int year);
}   
