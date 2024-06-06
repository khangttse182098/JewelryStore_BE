package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.PurchaseOrderRepositoryCustom;
import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.utils.NumberUtils;
import com.swp.jewelrystore.utils.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<PurchaseOrderEntity> findAllPurchaseOrder(Map<String, String> params) {
        // sql
        StringBuilder sql = new StringBuilder("SELECT purchaseorder.* FROM purchaseorder") ;
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        String groupby = " GROUP BY purchaseorder.purchase_order_id";
        sql.append(where);
        for(Map.Entry<String, String> param : params.entrySet()){
            if(NumberUtils.isLong(param.getValue())){
                sql.append(" AND " + param.getKey() + " = " + param.getValue());
            }else if(StringUtils.check(param.getValue())){
                if(param.getKey().contains("code")){
                    sql.append(" AND purchase_order_code LIKE '%" + param.getValue() + "%'");
                }else{
                    sql.append(" AND " + param.getKey() + " LIKE '%" + param.getValue() + "%'");
                }

            }
        }
        sql.append(groupby);
        System.out.println(sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString(), PurchaseOrderEntity.class);
        return query.getResultList();
    }

}
