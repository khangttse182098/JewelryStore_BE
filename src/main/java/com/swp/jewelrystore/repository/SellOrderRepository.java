package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.SellOrderRepositoryCustom;
import com.swp.jewelrystore.entity.SellOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellOrderRepository extends JpaRepository<SellOrderEntity, Long> , SellOrderRepositoryCustom {
    SellOrderEntity findBySellOrderCodeIs(String sellOrderCode);
}
