package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.entity.*;
import com.swp.jewelrystore.model.response.UserResponseDTO;
import com.swp.jewelrystore.repository.PurchaseOrderDetailRepository;
import com.swp.jewelrystore.repository.PurchaseOrderRepository;
import com.swp.jewelrystore.repository.SellOrderRepository;
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
    private final SellOrderRepository sellOrderRepository;

    public UserResponseDTO toUserResponseDTO(UserEntity userEntity) {
        UserResponseDTO userResponseDTO = modelMapper.map(userEntity, UserResponseDTO.class);
        userResponseDTO.setStatus(UserStatusConverter.convertStatusFromNumberToText(userEntity.getStatus()));
        double personalIncome = 0;
        // sell: lấy ra những hóa đơn mà đã thanh toán, tức không thuộc status "Chưa thanh toán"
        List<SellOrderEntity> sellOrderEntities = sellOrderRepository.findByUser_IdAndStatusNot(userEntity.getId(), SystemConstant.UNPAID);
        userResponseDTO.setSellOrderQuantity(sellOrderEntities.size());
        for (SellOrderEntity sellOrderEntity : sellOrderEntities) {
            List<SellOrderDetailEntity> sellOrderDetailEntities = sellOrderEntity.getSellOrderDetailEntities();
            for (SellOrderDetailEntity sellOrderDetailEntity : sellOrderDetailEntities) {
                personalIncome += sellOrderDetailEntity.getPrice();
            }
        }
        userResponseDTO.setRole(userEntity.getRole().getName());
        userResponseDTO.setPersonalIncome(personalIncome);
        return userResponseDTO;
    }
}
