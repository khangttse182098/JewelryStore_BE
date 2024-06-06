package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.SellOrderRepositoryCustom;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
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

    @Override
    public List<SellOrderEntity> findAllSellOrder(Map<String, String> params) {
        // sql
        StringBuilder sql = new StringBuilder("SELECT sellorder.* FROM sellorder") ;
        StringBuilder where = new StringBuilder(" WHERE 1=1");
        String groupby = " GROUP BY sellorder.sell_order_id";
        sql.append(where);
        for(Map.Entry<String, String> param : params.entrySet()){
            if(NumberUtils.isLong(param.getValue())){
                sql.append(" AND " + param.getKey() + " = " + param.getValue().trim());
            }else if(StringUtils.check(param.getValue())){
                if(param.getKey().contains("code")){
                    sql.append(" AND sell_order_code LIKE '%" + param.getValue().trim() + "%'");
                }else{
                    sql.append(" AND " + param.getKey() + " LIKE '%" + param.getValue().trim() + "%'");
                }
            }
        }
        sql.append(groupby);
        System.out.println(sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString(), SellOrderEntity.class);
        return query.getResultList();
    }
}
