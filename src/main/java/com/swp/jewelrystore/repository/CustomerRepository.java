package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.CustomerRepositoryCustom;
import com.swp.jewelrystore.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, CustomerRepositoryCustom {
     CustomerEntity findByFullNameOrPhoneNumber(String fullName, String phoneNumber);
     CustomerEntity findByPhoneNumber(String phoneNumber);
  }
