package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.UserRepositoryCustom;
import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.response.UserRevenueResponseDTO;
import com.swp.jewelrystore.utils.NumberUtils;
import com.swp.jewelrystore.utils.SearchOrderUtils;
import com.swp.jewelrystore.utils.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserEntity> getAllUsers(Map<String, String> params) {
        // sql
        StringBuilder sql = new StringBuilder("SELECT user.* FROM user ") ;
        StringBuilder where = new StringBuilder(" WHERE 1=1 AND user.role_id <> 1");
        String groupby = " GROUP BY user.user_id";
        //join conditions
        if(params.containsKey("roleName")){
            sql.append("JOIN role ON user.role_id = role.role_id ");
        }
        sql.append(where) ;
        for(Map.Entry<String, String> param : params.entrySet()){
            if(param.getKey().equals("phone")){
                sql.append(" AND user.phone LIKE '%" + param.getValue().trim() + "%'");
            }
            else if(NumberUtils.isLong(param.getValue())){
                sql.append(" AND " + param.getKey() + " = " + param.getValue());
            }
            else if(StringUtils.check(param.getValue())){
                if(param.getKey().equals("roleName")){
                    sql.append(" AND role.name LIKE '%" + param.getValue().trim() + "%'");
                }
                else{
                    sql.append(" AND " + param.getKey() + " LIKE '%" + param.getValue().trim() + "%'");
                }
            }
        }
        sql.append(groupby) ;
        System.out.println(sql);
        Query query = entityManager.createNativeQuery(sql.toString(), UserEntity.class);
        return query.getResultList();
    }

    @Override
        public List<UserRevenueResponseDTO> getUserRevenue(Map<String, String> params) {
            StringBuilder sql = new StringBuilder("SELECT user.user_id, user.fullname, SUM(sellorderdetail.price) as revenue FROM user");
            sql.append(" LEFT JOIN sellorder ON sellorder.user_id = user.user_id ");
            sql.append(" LEFT JOIN sellorderdetail ON sellorderdetail.sell_order_id = sellorder.sell_order_id");
            StringBuilder where = new StringBuilder(" WHERE user.role_id = 3 AND sellorder.status NOT LIKE 'Chưa thanh toán' ");
            SearchOrderUtils.queryWhereSpecial(where,params);
            String groupby = " GROUP BY user.user_id ";
            sql.append(where);
            sql.append(groupby);
            System.out.println(sql);
            Query query = entityManager.createNativeQuery(sql.toString());
            List<Object[]> resultList = query.getResultList();
            List<UserRevenueResponseDTO> userRevenueResponseDTOS = new ArrayList<>();
            for (Object[] result : resultList) {
                BigInteger id = (BigInteger) result[0];
                String fullname = (String) result[1];
                BigDecimal revenue = (BigDecimal) result[2];
                userRevenueResponseDTOS.add(new UserRevenueResponseDTO(id.longValue() ,fullname, revenue.toBigIntegerExact().doubleValue()));
            }
            return userRevenueResponseDTOS;
        }
}
