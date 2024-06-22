package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.converter.RevenueByDateConverter;
import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.model.response.RevenueByDateResponseDTO;
import com.swp.jewelrystore.model.response.RevenueResponseDTO;
import com.swp.jewelrystore.repository.SellOrderRepository;
import com.swp.jewelrystore.service.IOrderService;
import com.swp.jewelrystore.service.IRevenueDashboardService;
import com.swp.jewelrystore.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RevenueDashboardService implements IRevenueDashboardService {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private DateTimeConverter dateTimeConverter;
    @Autowired
    private SellOrderRepository sellOrderRepository;

    @Autowired
    private RevenueByDateConverter revenueByDateConverter;

    @Override
    public RevenueResponseDTO getTotalRevenueByTime(Map<String, String> params) {
        List<InvoiceResponseDTO> invoiceList = orderService.getAllOrder(params) ;
        double totalSellRevenue = 0;
        double totalPurchaseRevenue = 0;
        RevenueResponseDTO revenueResponseDTO = new RevenueResponseDTO();
        for (InvoiceResponseDTO invoiceResponseDTO : invoiceList) {
            RevenueByDateResponseDTO revenueByDateResponseDTO = new RevenueByDateResponseDTO();
            if(invoiceResponseDTO.getInvoiceCode().startsWith("SELL")){
                totalSellRevenue += invoiceResponseDTO.getTotalPrice();
            }
            if(invoiceResponseDTO.getInvoiceCode().startsWith("PURC")){
                totalPurchaseRevenue += invoiceResponseDTO.getTotalPrice();
            }
        }
        revenueResponseDTO.setTotalSellRevenue(totalSellRevenue);
        revenueResponseDTO.setTotalPurchaseRevenue(totalPurchaseRevenue);

        // getByDate
        List<RevenueByDateResponseDTO> sellRevenueByDate = getSellRevenueByDate(params);
        revenueResponseDTO.setSellRevenueByDate(sellRevenueByDate);
        return revenueResponseDTO;
    }
    public List<RevenueByDateResponseDTO> getSellRevenueByDate(Map<String, String> params) {
        List<RevenueByDateResponseDTO> sellRevenueByDate = new ArrayList<>();
        if(params.get("time") != null){
            String time = params.get("time").trim();
            if(StringUtils.check(time)){
                switch (time){
                    case "7days" :
                        for (int i = 0; i < 7; i++) {
                            sellRevenueByDate.add(revenueByDateConverter.toSellRevenueByDateResponseDTO(i));
                        }
                        return sellRevenueByDate;
                    case "30days" :
                        for (int i = 0; i < 30; i++) {
                            sellRevenueByDate.add(revenueByDateConverter.toSellRevenueByDateResponseDTO(i));
                        }
                        return sellRevenueByDate;
                    case "12months" :
                        LocalDate currentDate = LocalDate.now();
                        LocalDate date12MonthsAgo = currentDate.minusMonths(12);
                        int dateIn12Months = (int) ChronoUnit.DAYS.between(date12MonthsAgo, currentDate);
                        for (int i = 0; i < dateIn12Months; i++) {
                            sellRevenueByDate.add(revenueByDateConverter.toSellRevenueByDateResponseDTO(i));
                        }
                        return sellRevenueByDate;
                    case "alltime":
                        break;
                }
            }
        }
        return sellRevenueByDate;
    }



}
