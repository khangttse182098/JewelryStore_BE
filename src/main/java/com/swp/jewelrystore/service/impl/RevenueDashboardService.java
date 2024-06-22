package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.model.response.RevenueByDateResponseDTO;
import com.swp.jewelrystore.model.response.RevenueResponseDTO;
import com.swp.jewelrystore.service.IOrderService;
import com.swp.jewelrystore.service.IRevenueDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RevenueDashboardService implements IRevenueDashboardService {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private DateTimeConverter dateTimeConverter;

    @Override
    public RevenueResponseDTO getTotalRevenueByTime(Map<String, String> params) {
        List<InvoiceResponseDTO> invoiceList = orderService.getAllOrder(params) ;
        double totalSellRevenue = 0;
        double totalPurchaseRevenue = 0;
        RevenueResponseDTO revenueResponseDTO = new RevenueResponseDTO();
        for (InvoiceResponseDTO invoiceResponseDTO : invoiceList) {
            if(invoiceResponseDTO.getInvoiceCode().startsWith("SELL")){
                totalSellRevenue += invoiceResponseDTO.getTotalPrice();
            }
            if(invoiceResponseDTO.getInvoiceCode().startsWith("PURC")){
                totalPurchaseRevenue += invoiceResponseDTO.getTotalPrice();
            }
        }
        revenueResponseDTO.setTotalSellRevenue(totalSellRevenue);
        revenueResponseDTO.setTotalPurchaseRevenue(totalPurchaseRevenue);
        return revenueResponseDTO;
    }


}
