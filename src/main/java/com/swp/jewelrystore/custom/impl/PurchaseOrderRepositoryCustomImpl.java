package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.PurchaseOrderRepositoryCustom;
import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class PurchaseOrderRepositoryCustomImpl implements PurchaseOrderRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String generatePurchaseOrderCode() {
        String purchaseOrderCode = "PURC";
        int number = countTotalPurchaseOrders() + 1;
        purchaseOrderCode += String.valueOf(number);
        return purchaseOrderCode;
    }

    @Override
    public int countTotalPurchaseOrders() {
        String sql = "SELECT purchaseorder.* FROM purchaseorder";
        Query query = entityManager.createNativeQuery(sql, PurchaseOrderEntity.class);
        return query.getResultList().size();
    }

}
