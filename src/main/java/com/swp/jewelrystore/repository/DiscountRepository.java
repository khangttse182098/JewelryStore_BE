package com.swp.jewelrystore.repository;


import com.swp.jewelrystore.custom.DiscountRepositoryCustom;
import com.swp.jewelrystore.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Long>, DiscountRepositoryCustom {
}
