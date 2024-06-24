package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.entity.PurchaseOrderEntity;
import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.response.RevenueByDateResponseDTO;
import com.swp.jewelrystore.repository.PurchaseOrderRepository;
import com.swp.jewelrystore.repository.SellOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Component
public class RevenueByDateConverter {
    @Autowired
    private SellOrderRepository sellOrderRepository;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;


    public RevenueByDateResponseDTO toSellRevenueByDateResponseDTO(int minusDay) {
        RevenueByDateResponseDTO sellRevenueByDateResponseDTO = new RevenueByDateResponseDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate = LocalDate.now();
        LocalDate date = currentDate.minusDays(minusDay);
        String formattedDate = date.format(formatter);
        sellRevenueByDateResponseDTO.setCreatedDate(formattedDate);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String searchFormattedDate = date.format(formatter);
        List<SellOrderEntity> sellOrderEntities = sellOrderRepository.findByCreatedDateCustom(searchFormattedDate);
        double totalPrice = 0;
        Long numberOfOrder = 0L;
        if(!sellOrderEntities.isEmpty() && sellOrderEntities != null) {
            for (SellOrderEntity sellOrderEntity : sellOrderEntities) {
                totalPrice += sellOrderRepository.getTotalRevenue(sellOrderEntity);
                if(!sellOrderEntity.getStatus().equals(SystemConstant.UNPAID)){
                    numberOfOrder ++;
                }
            }
        }
        sellRevenueByDateResponseDTO.setTotalPrice(totalPrice);
        sellRevenueByDateResponseDTO.setNumberOfOrder(numberOfOrder);
        return sellRevenueByDateResponseDTO;
    }

    public RevenueByDateResponseDTO toPurchaseRevenueByDateResponseDTO(int minusDay) {
        RevenueByDateResponseDTO purchaseRevenueByDateResponseDTO = new RevenueByDateResponseDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate = LocalDate.now();
        LocalDate date = currentDate.minusDays(minusDay);
        String formattedDate = date.format(formatter);
        purchaseRevenueByDateResponseDTO.setCreatedDate(formattedDate);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String searchFormattedDate = date.format(formatter);
        List<PurchaseOrderEntity> purchaseOrderEntities = purchaseOrderRepository.findByCreatedDateCustom(searchFormattedDate);
        double totalPrice = 0;
        Long numberOfOrder = 0L;
        if(!purchaseOrderEntities.isEmpty() && purchaseOrderEntities != null) {
            for (PurchaseOrderEntity purchaseOrderEntity : purchaseOrderEntities) {
                totalPrice += purchaseOrderRepository.getTotalRevenue(purchaseOrderEntity);
                if(!purchaseOrderEntity.getStatus().equals(SystemConstant.UNPAID)){
                    numberOfOrder++;
                }
            }
        }
        purchaseRevenueByDateResponseDTO.setTotalPrice(totalPrice);
        purchaseRevenueByDateResponseDTO.setNumberOfOrder(numberOfOrder);
        numberOfOrder = 0L;
        return purchaseRevenueByDateResponseDTO;
    }
}
