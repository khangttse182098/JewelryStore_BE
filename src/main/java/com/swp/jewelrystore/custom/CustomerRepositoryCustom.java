package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.CustomerEntity;

import java.util.List;
import java.util.Map;

public interface CustomerRepositoryCustom {
    List<CustomerEntity> findByPhoneNumberCustom(Map<String, String> phoneNumber);
}
