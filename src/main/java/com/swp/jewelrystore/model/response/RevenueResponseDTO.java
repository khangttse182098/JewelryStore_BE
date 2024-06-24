package com.swp.jewelrystore.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevenueResponseDTO {
    // totalRevenue
    private Double totalSellRevenue;
    private Double totalPurchaseRevenue;
    // sellRevenue
    List<String> createdDateList;
    List<Long> numberOfSellOrderList;
    List<Double> sellTotalPriceList;
    // purchaseRevenue
    List<Long> numberOfPurchaseOrderList;
    List<Double> purchaseTotalPriceList;

}
