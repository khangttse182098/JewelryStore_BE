package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.SellOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellOrderRepository extends JpaRepository<SellOrderEntity, Long> {
    SellOrderEntity findBySellOrderCodeIs(String sellOrderCode);
}
