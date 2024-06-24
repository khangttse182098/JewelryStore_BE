package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.converter.RevenueByDateConverter;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.response.InvoiceResponseDTO;
import com.swp.jewelrystore.model.response.RevenueByDateResponseDTO;
import com.swp.jewelrystore.model.response.RevenueResponseDTO;
import com.swp.jewelrystore.repository.PurchaseOrderRepository;
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
    private SellOrderRepository sellOrderRepository;

    @Autowired
    private RevenueByDateConverter revenueByDateConverter;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

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

        // getSellRevenueByDate
        RevenueResponseDTO sellTmpRevenueResponseDTO = getSellRevenueByDate(params);
        revenueResponseDTO.setCreatedDateList(sellTmpRevenueResponseDTO.getCreatedDateList());
        revenueResponseDTO.setSellTotalPriceList(sellTmpRevenueResponseDTO.getSellTotalPriceList());
        revenueResponseDTO.setNumberOfSellOrderList(sellTmpRevenueResponseDTO.getNumberOfSellOrderList());

        // getPurchaseRevenueByDate
        RevenueResponseDTO purchaseTmpRevenueResponseDTO = getPurchaseRevenueByDate(params);
        revenueResponseDTO.setPurchaseTotalPriceList(purchaseTmpRevenueResponseDTO.getPurchaseTotalPriceList());
        revenueResponseDTO.setNumberOfPurchaseOrderList(purchaseTmpRevenueResponseDTO.getNumberOfPurchaseOrderList());
        return revenueResponseDTO;
    }
    public RevenueResponseDTO getSellRevenueByDate(Map<String, String> params) {
        List<String> sellCreatedDateList = new ArrayList<>();
        List<Double> sellTotalPriceList = new ArrayList<>();
        List<Long> numberOfSellOrderList = new ArrayList<>();
        RevenueResponseDTO tmpRevenueResponseDTO = new RevenueResponseDTO();
        if(params.get("startDate") != null){
            String startDate = params.get("startDate").trim();
            if(StringUtils.check(startDate)){

            }
        }
        if(params.get("time") != null){
            String time = params.get("time").trim();
            if(StringUtils.check(time)){
                switch (time){
                    case "7days" :
                        for (int i = 0; i < 7; i ++) {
                            sellCreatedDateList.add(revenueByDateConverter.toSellRevenueByDateResponseDTO(i).getCreatedDate());
                            sellTotalPriceList.add(revenueByDateConverter.toSellRevenueByDateResponseDTO(i).getTotalPrice());
                            numberOfSellOrderList.add(revenueByDateConverter.toSellRevenueByDateResponseDTO(i).getNumberOfOrder());
                        }
                        tmpRevenueResponseDTO.setCreatedDateList(sellCreatedDateList);
                        tmpRevenueResponseDTO.setSellTotalPriceList(sellTotalPriceList);
                        tmpRevenueResponseDTO.setNumberOfSellOrderList(numberOfSellOrderList);
                        return tmpRevenueResponseDTO;
                    case "4weeks" :
                        int flag = 0;
                        String endDate = "";
                        String startDate = "";
                        double totalPrice = 0;
                        Long numberOfOrder = 0L;
                        for (int i = 0; i < 30; i ++) {
                            RevenueByDateResponseDTO sellRevenueByDateResponseDTO = revenueByDateConverter.toSellRevenueByDateResponseDTO(i);
                            flag ++;
                            totalPrice += sellRevenueByDateResponseDTO.getTotalPrice();
                            numberOfOrder += sellRevenueByDateResponseDTO.getNumberOfOrder();
                            if(flag == 1){
                                endDate = sellRevenueByDateResponseDTO.getCreatedDate();
                            }else if(flag == 7){
                                startDate = sellRevenueByDateResponseDTO.getCreatedDate();
                                String result = startDate + " đến " + endDate;
                                sellCreatedDateList.add(result);
                                sellTotalPriceList.add(totalPrice);
                                numberOfSellOrderList.add(numberOfOrder);
                                totalPrice = 0;
                                flag = 0;
                                numberOfOrder = 0L;
                            }
                        }
                        tmpRevenueResponseDTO.setCreatedDateList(sellCreatedDateList);
                        tmpRevenueResponseDTO.setSellTotalPriceList(sellTotalPriceList);
                        tmpRevenueResponseDTO.setNumberOfSellOrderList(numberOfSellOrderList);
                        return tmpRevenueResponseDTO;
                    case "12months" :
                        LocalDate currentDate = LocalDate.now();
                        for (int i = 0; i < 12; i++) {
                            double totalRevenue = 0;
                            Long numberOfOrders = 0L;
                            LocalDate dateLastMonths = currentDate.minusMonths(i);
                            List<SellOrderEntity> sellOrderEntities = sellOrderRepository.findByCreatedDateMonth(dateLastMonths.getMonthValue(), dateLastMonths.getYear());
                            if(!sellOrderEntities.isEmpty() && sellOrderEntities != null){
                                for (SellOrderEntity sellOrderEntity : sellOrderEntities) {
                                   totalRevenue += sellOrderRepository.getTotalRevenue(sellOrderEntity);
                                    if(!sellOrderEntity.getStatus().equals(SystemConstant.UNPAID)){
                                        numberOfOrders ++;
                                    }
                                }
                            }
                            String createdDate = dateLastMonths.format(DateTimeFormatter.ofPattern("MM-yyyy"));
                            sellCreatedDateList.add(createdDate);
                            sellTotalPriceList.add(totalRevenue);
                            numberOfSellOrderList.add(numberOfOrders);
                        }
                        tmpRevenueResponseDTO.setCreatedDateList(sellCreatedDateList);
                        tmpRevenueResponseDTO.setSellTotalPriceList(sellTotalPriceList);
                        tmpRevenueResponseDTO.setNumberOfSellOrderList(numberOfSellOrderList);
                        return tmpRevenueResponseDTO;
                    case "alltime":
                        break;
                }
            }
        }
        return tmpRevenueResponseDTO;
    }

    public RevenueResponseDTO getPurchaseRevenueByDate(Map<String, String> params) {
        List<Double> purchaseTotalPriceList = new ArrayList<>();
        List<Long> numberOfPurchaseOrderList = new ArrayList<>();
        RevenueResponseDTO tmpRevenueResponseDTO = new RevenueResponseDTO();
        if(params.get("time") != null){
            String time = params.get("time").trim();
            if(StringUtils.check(time)){
                switch (time){
                    case "7days" :
                        for (int i = 0; i < 7; i ++) {
                            purchaseTotalPriceList.add(revenueByDateConverter.toPurchaseRevenueByDateResponseDTO(i).getTotalPrice());
                            numberOfPurchaseOrderList.add(revenueByDateConverter.toPurchaseRevenueByDateResponseDTO(i).getNumberOfOrder());
                        }
                        tmpRevenueResponseDTO.setPurchaseTotalPriceList(purchaseTotalPriceList);
                        tmpRevenueResponseDTO.setNumberOfPurchaseOrderList(numberOfPurchaseOrderList);
                        return tmpRevenueResponseDTO;
                    case "4weeks" :
                        int flag = 0;
                        double totalPrice = 0;
                        Long numberOfOrder = 0L;
                        for (int i = 0; i < 30; i ++) {
                            RevenueByDateResponseDTO purchaseRevenueByDateResponseDTO = revenueByDateConverter.toPurchaseRevenueByDateResponseDTO(i);
                            flag ++;
                            totalPrice += purchaseRevenueByDateResponseDTO.getTotalPrice();
                            numberOfOrder += purchaseRevenueByDateResponseDTO.getNumberOfOrder();
                            if(flag == 7){
                                purchaseTotalPriceList.add(totalPrice);
                                numberOfPurchaseOrderList.add(numberOfOrder);
                                totalPrice = 0;
                                flag = 0;
                                numberOfOrder = 0L;
                            }
                        }
                        tmpRevenueResponseDTO.setPurchaseTotalPriceList(purchaseTotalPriceList);
                        tmpRevenueResponseDTO.setNumberOfPurchaseOrderList(numberOfPurchaseOrderList);
                        return tmpRevenueResponseDTO;
                    case "12months" :
                        LocalDate currentDate = LocalDate.now();
                        for (int i = 0; i < 12; i++) {
                            double totalRevenue = 0;
                            Long numberOfOrders = 0L;
                            LocalDate dateLastMonths = currentDate.minusMonths(i);
                            List<PurchaseOrderEntity> purchaseOrderEntities = purchaseOrderRepository.findByCreatedDateMonth(dateLastMonths.getMonthValue(), dateLastMonths.getYear());
                            if(!purchaseOrderEntities.isEmpty() && purchaseOrderEntities != null){
                                for (PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
                                    totalRevenue += purchaseOrderRepository.getTotalRevenue(purchaseOrderEntity);
                                    if(!purchaseOrderEntity.getStatus().equals(SystemConstant.UNPAID)){
                                        numberOfOrders ++;
                                    }
                                }
                            }
                            purchaseTotalPriceList.add(totalRevenue);
                            numberOfPurchaseOrderList.add(numberOfOrders);
                        }
                        tmpRevenueResponseDTO.setPurchaseTotalPriceList(purchaseTotalPriceList);
                        tmpRevenueResponseDTO.setNumberOfPurchaseOrderList(numberOfPurchaseOrderList);
                        return tmpRevenueResponseDTO;
                    case "alltime":
                        break;
                }
            }
        }
        return tmpRevenueResponseDTO;
    }
}
