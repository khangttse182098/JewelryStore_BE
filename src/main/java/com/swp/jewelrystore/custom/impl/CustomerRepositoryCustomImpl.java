package com.swp.jewelrystore.custom.impl;

import com.swp.jewelrystore.custom.CustomerRepositoryCustom;
import com.swp.jewelrystore.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository
public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CustomerEntity> findByPhoneNumberCustom(Map<String, String> phoneNumber) {
        StringBuilder sql = new StringBuilder("SELECT * FROM customer");
        StringBuilder where = new StringBuilder(" WHERE 1 = 1 ");
        if (!phoneNumber.containsKey("phoneNumber") || phoneNumber.get("phoneNumber").isEmpty()) {
            sql.append(where);
            System.out.println(sql);
            Query query = entityManager.createNativeQuery(sql.toString(), CustomerEntity.class);
            return query.getResultList();
        }
        where.append(" AND phone_number LIKE '%" + phoneNumber.get("phoneNumber") + "%'");
        sql.append(where);
        System.out.println(sql);
        Query query = entityManager.createNativeQuery(sql.toString(), CustomerEntity.class);
        return query.getResultList();
    }
}
