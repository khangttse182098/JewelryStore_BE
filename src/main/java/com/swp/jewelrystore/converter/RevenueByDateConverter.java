package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.model.response.RevenueByDateResponseDTO;
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


    public RevenueByDateResponseDTO toSellRevenueByDateResponseDTO(int minusDay) {
        RevenueByDateResponseDTO sellRevenueByDateResponseDTO = new RevenueByDateResponseDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate currentDate = LocalDate.now();
        LocalDate date = currentDate.minusDays(minusDay);
        String formattedDate = date.format(formatter);
        sellRevenueByDateResponseDTO.setCreatedDate(formattedDate);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String searchFormattedDate = date.format(formatter);
        SellOrderEntity sellOrderEntity = sellOrderRepository.findByCreatedDateCustom(searchFormattedDate);
        double totalPrice = 0;
        if(sellOrderEntity != null) {
            List<SellOrderDetailEntity> sellOrderDetailEntityList = sellOrderEntity.getSellOrderDetailEntities();
            for (SellOrderDetailEntity sellOrderDetailEntity : sellOrderDetailEntityList) {
                totalPrice += sellOrderDetailEntity.getPrice();
            }
        }
        sellRevenueByDateResponseDTO.setTotalPrice(totalPrice);
        return sellRevenueByDateResponseDTO;
    }
}
