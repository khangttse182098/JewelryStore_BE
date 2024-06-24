package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.custom.PurchaseOrderRepositoryCustom;
import com.swp.jewelrystore.entity.*;
import com.swp.jewelrystore.utils.NumberUtils;
import com.swp.jewelrystore.utils.SearchOrderUtils;
import com.swp.jewelrystore.utils.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class PurchaseOrderRepositoryCustomImpl implements PurchaseOrderRepositoryCustom {
    private final DateTimeConverter dateTimeConverter;
    @PersistenceContext
    private EntityManager entityManager;

    public PurchaseOrderRepositoryCustomImpl(DateTimeConverter dateTimeConverter) {
        this.dateTimeConverter = dateTimeConverter;
    }

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

    @Override
    public List<PurchaseOrderEntity> findAllPurchaseOrder(Map<String, String> params) {
        // sql
        StringBuilder sql = new StringBuilder("SELECT purchaseorder.* FROM purchaseorder") ;
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        String groupby = " GROUP BY purchaseorder.purchase_order_id";
        SearchOrderUtils.queryWhereNormal(where,params);
        SearchOrderUtils.queryWhereSpecial(where,params);
        if(StringUtils.check(params.get("code"))){
            sql.append(" AND sell_order_code LIKE '%" + params.get("code").trim() + "%'");
        }
        sql.append(where);
        sql.append(groupby);
        System.out.println(sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString(), PurchaseOrderEntity.class);
        return query.getResultList();
    }

    @Override
    public List<PurchaseOrderEntity> findByCreatedDateCustom(String createdDate) {
        String sql = "SELECT * FROM purchaseorder WHERE DATE (created_date) = '" + createdDate + "'";
        Query query = entityManager.createNativeQuery(sql.toString(), PurchaseOrderEntity.class);
        return query.getResultList();
    }

    @Override
    public double getTotalRevenue(PurchaseOrderEntity purchaseOrderEntity) {
        double totalPrice = 0;
        if(!purchaseOrderEntity.getStatus().equals(SystemConstant.UNPAID)){
            for(PurchaseOrderDetailEntity purchaseOrderDetailEntity : purchaseOrderEntity.getPurchaseOrderDetailEntities()){
                totalPrice += purchaseOrderDetailEntity.getPrice();
            }
        }
        return totalPrice;
    }

}
