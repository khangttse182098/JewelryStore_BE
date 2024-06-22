package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RevenueResponseDTO {
    private Double totalSellRevenue;
    private Double totalPurchaseRevenue;
    List<RevenueByDateResponseDTO> sellRevenueByDate;
    List<RevenueByDateResponseDTO> purchaseRevenueByDate;

}
