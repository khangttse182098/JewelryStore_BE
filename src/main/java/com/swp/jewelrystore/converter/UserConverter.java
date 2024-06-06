package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.entity.SellOrderDetailEntity;
import com.swp.jewelrystore.entity.SellOrderEntity;
import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.response.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserConverter {
    @Autowired
    private ModelMapper modelMapper;
    public UserResponseDTO toUserResponseDTO(UserEntity userEntity) {
        UserResponseDTO userResponseDTO = modelMapper.map(userEntity, UserResponseDTO.class);
        double personalIncome = 0;
        List<SellOrderEntity> sellOrderEntities = userEntity.getSellOrderEntities();
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
