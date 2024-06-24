package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;

import java.util.List;
import java.util.Map;

public interface PurchaseOrderRepositoryCustom {
    String generatePurchaseOrderCode();
    int countTotalPurchaseOrders();
    List<PurchaseOrderEntity> findAllPurchaseOrder(Map<String, String> params);
    List<PurchaseOrderEntity> findByCreatedDateCustom(String createdDate);
    double getTotalRevenue(PurchaseOrderEntity purchaseOrderEntity);
}
