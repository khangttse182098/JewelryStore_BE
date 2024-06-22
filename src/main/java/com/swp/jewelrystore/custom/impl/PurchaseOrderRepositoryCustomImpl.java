package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.custom.PurchaseOrderRepositoryCustom;
import com.swp.jewelrystore.entity.ProductEntity;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
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
    public void queryWhereSpecial(StringBuilder sql, Map<String, String> params) {
        String startDateString = params.get("startDate").trim();
        if(StringUtils.check(startDateString)){
            Date startDate = dateTimeConverter.convertToDateTimeDTO(startDateString);
            sql.append(" AND created_date >= " + startDate.toString());
        }
        String endDateString = params.get("endDate").trim();
        if(StringUtils.check(endDateString)){
            Date endDate = dateTimeConverter.convertToDateTimeDTO(endDateString);
            sql.append(" AND created_date <= " + endDate.toString());
        }
        String time = params.get("time").trim();
        if(StringUtils.check(time)){
            switch (time){
                case "7days" :
                    sql.append("AND created_date >= CURDATE() - INTERVAL 7 DAY");
                    break;
                case "30days" :
                    sql.append("AND created_date >= CURDATE() - INTERVAL 30 DAY");
                    break;
                case "12months" :
                    sql.append("AND created_date >= CURDATE() - INTERVAL 12 MONTH");
                    break;
                case "alltime":
                    break;
            }
        }
        String code = params.get("code").trim();
        if(StringUtils.check(code)){
            sql.append(" AND sell_order_code LIKE '%" + code + "%'");
        }
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

}
