package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.UserRepositoryCustom;
import com.swp.jewelrystore.entity.UserEntity;
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
}
