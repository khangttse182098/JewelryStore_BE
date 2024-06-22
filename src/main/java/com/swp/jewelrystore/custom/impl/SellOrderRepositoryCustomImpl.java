package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.custom.SellOrderRepositoryCustom;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.utils.NumberUtils;
import com.swp.jewelrystore.utils.SearchOrderUtils;
import com.swp.jewelrystore.utils.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Array;
import java.util.*;

@Repository
public class SellOrderRepositoryCustomImpl implements SellOrderRepositoryCustom {
    public static DateTimeConverter dateTimeConverter;
    @PersistenceContext
    private EntityManager entityManager;

    public SellOrderRepositoryCustomImpl(DateTimeConverter dateTimeConverter) {
        this.dateTimeConverter = dateTimeConverter;
    }

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
    @Override
    public List<SellOrderEntity> findAllSellOrder(Map<String, String> params) {
        // sql
        StringBuilder sql = new StringBuilder("SELECT sellorder.* FROM sellorder") ;
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        String groupby = " GROUP BY sellorder.sell_order_id";
        SearchOrderUtils.queryWhereNormal(where, params);
        SearchOrderUtils.queryWhereSpecial(where, params);
        if(StringUtils.check(params.get("code"))){
            sql.append(" AND sell_order_code LIKE '%" + params.get("code").trim() + "%'");
        }
        sql.append(where);
        sql.append(groupby);
        System.out.println(sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString(), SellOrderEntity.class);
        return query.getResultList();
    }
}
