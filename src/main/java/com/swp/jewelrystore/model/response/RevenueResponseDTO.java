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
    private Double totalSellRevenue;
    private Double totalPurchaseRevenue;
    List<String> sellCreatedDateList;
    List<Long> numberOfSellOrderList;
    List<Double> sellTotalPriceList;
    List<String> purchaseCreatedDateList;
    List<Double> purchaseTotalPriceList;

}
