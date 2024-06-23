package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.constant.SystemConstant;
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
            if(invoiceResponseDTO.getInvoiceCode().startsWith("SELL") && !invoiceResponseDTO.getStatus().equals(SystemConstant.UNPAID)){
                totalSellRevenue += invoiceResponseDTO.getTotalPrice();
            }
            if(invoiceResponseDTO.getInvoiceCode().startsWith("PURC") && !invoiceResponseDTO.getStatus().equals(SystemConstant.UNPAID)){
                totalPurchaseRevenue += invoiceResponseDTO.getTotalPrice();
            }
        }
        revenueResponseDTO.setTotalSellRevenue(totalSellRevenue);
        revenueResponseDTO.setTotalPurchaseRevenue(totalPurchaseRevenue);

        // getByDate
        RevenueResponseDTO tmpRevenueResponseDTO = getSellRevenueByDate(params);
        revenueResponseDTO.setSellCreatedDateList(tmpRevenueResponseDTO.getSellCreatedDateList());
        revenueResponseDTO.setSellTotalPriceList(tmpRevenueResponseDTO.getSellTotalPriceList());
        return revenueResponseDTO;
    }
    public RevenueResponseDTO getSellRevenueByDate(Map<String, String> params) {
        List<String> sellCreatedDateList = new ArrayList<>();
        List<Double> sellTotalPriceList = new ArrayList<>();
        RevenueResponseDTO tmpRevenueResponseDTO = new RevenueResponseDTO();
        if(params.get("time") != null){
            String time = params.get("time").trim();
            if(StringUtils.check(time)){
                switch (time){
                    case "7days" :
                        for (int i = 0; i < 7; i ++) {
                            sellCreatedDateList.add(revenueByDateConverter.toSellRevenueByDateResponseDTO(i).getCreatedDate());
                            sellTotalPriceList.add(revenueByDateConverter.toSellRevenueByDateResponseDTO(i).getTotalPrice());
                        }
                        tmpRevenueResponseDTO.setSellCreatedDateList(sellCreatedDateList);
                        tmpRevenueResponseDTO.setSellTotalPriceList(sellTotalPriceList);
                        return tmpRevenueResponseDTO;
                    case "4weeks" :
                        int flag = 0;
                        String endDate = "";
                        String startDate = "";
                        double totalPrice = 0;
                        for (int i = 0; i < 30; i ++) {
                            RevenueByDateResponseDTO sellRevenueByDateResponseDTO = revenueByDateConverter.toSellRevenueByDateResponseDTO(i);
                            flag ++;
                            totalPrice += sellRevenueByDateResponseDTO.getTotalPrice();
                            if(flag == 1){
                                endDate = sellRevenueByDateResponseDTO.getCreatedDate();
                            }else if(flag == 7){
                                startDate = sellRevenueByDateResponseDTO.getCreatedDate();
                                String result = startDate + " đến " + endDate;
                                sellCreatedDateList.add(result);
                                sellTotalPriceList.add(totalPrice);
                                totalPrice = 0;
                                flag = 0;
                            }
                        }
                        tmpRevenueResponseDTO.setSellCreatedDateList(sellCreatedDateList);
                        tmpRevenueResponseDTO.setSellTotalPriceList(sellTotalPriceList);
                        return tmpRevenueResponseDTO;
                    case "12months" :
                        LocalDate currentDate = LocalDate.now();
                        LocalDate date12MonthsAgo = currentDate.minusMonths(12);
                        int dateIn12Months = (int) ChronoUnit.DAYS.between(date12MonthsAgo, currentDate);
                        for (int i = 0; i < dateIn12Months; i++) {
//                            tmpRevenueResponseDTO.add(revenueByDateConverter.toSellRevenueByDateResponseDTO(i));
                        }
                        return tmpRevenueResponseDTO;
                    case "alltime":
                        break;
                }
            }
        }
        return tmpRevenueResponseDTO;
    }



}
