package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.entity.*;
import com.swp.jewelrystore.model.response.UserResponseDTO;
import com.swp.jewelrystore.repository.PurchaseOrderDetailRepository;
import com.swp.jewelrystore.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor

public class UserConverter {

    private final ModelMapper modelMapper;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseOrderDetailRepository purchaseOrderDetailRepository;

    public UserResponseDTO toUserResponseDTO(UserEntity userEntity) {
        UserResponseDTO userResponseDTO = modelMapper.map(userEntity, UserResponseDTO.class);
        userResponseDTO.setStatus(UserStatusConverter.convertNumberToText(userEntity.getStatus()));
        double personalIncome = 0;
        // sell
        List<SellOrderEntity> sellOrderEntities = userEntity.getSellOrderEntities();
        for (SellOrderEntity sellOrderEntity : sellOrderEntities) {
            List<SellOrderDetailEntity> sellOrderDetailEntities = sellOrderEntity.getSellOrderDetailEntities();
            for (SellOrderDetailEntity sellOrderDetailEntity : sellOrderDetailEntities) {
                personalIncome += sellOrderDetailEntity.getPrice();
            }
        }
        // purchase
        List<PurchaseOrderEntity> purchaseOrderEntities = purchaseOrderRepository.findByUserIdAndStatusNot(userEntity.getId(), SystemConstant.UNPAID);
        List<PurchaseOrderDetailEntity> purchaseOrderDetailEntities = purchaseOrderDetailRepository.findByPurchaseOrderIn(purchaseOrderEntities);
        double purchaseIncome = purchaseOrderDetailEntities.stream()
                .mapToDouble(PurchaseOrderDetailEntity::getPrice)
                .sum();
        userResponseDTO.setRole(userEntity.getRole().getName());
        userResponseDTO.setPersonalIncome(personalIncome);
        return userResponseDTO;
    }
}
