package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.SellOrderEntity;

import java.util.List;
import java.util.Map;

public interface SellOrderRepositoryCustom {
    int countTotalSellOrder();
    String generateSellOrderCode();
    List<SellOrderEntity> findAllSellOrder(Map<String, String> params);
}
