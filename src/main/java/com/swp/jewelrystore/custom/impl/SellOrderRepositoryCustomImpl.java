package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.SellOrderRepositoryCustom;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class SellOrderRepositoryCustomImpl implements SellOrderRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int countTotalSellOrder() {
        String sql = "SELECT sellorder.* FROM sellorder";
        Query query = entityManager.createNativeQuery(sql, SellOrderEntity.class);
        return query.getResultList().size();
    }

    @Override
    public String generateSellOrderCode() {
        String sellOrderCode = "SELL";
        int number = countTotalSellOrder() + 1;
        sellOrderCode += String.valueOf(number);
        return sellOrderCode;
    }
}
