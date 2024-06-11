package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.entity.PurchaseOrderDetailEntity;
import com.swp.jewelrystore.model.response.DiamondCriteriaResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DiamondCriteriaConverter {
    @Autowired
    private ModelMapper modelMapper;

    public DiamondCriteriaResponseDTO toDiamondCriteriaResponseDTO(PurchaseOrderDetailEntity purchaseOrderDetailEntity){
        DiamondCriteriaResponseDTO diamondCriteriaResponseDTO = modelMapper.map(purchaseOrderDetailEntity, DiamondCriteriaResponseDTO.class);
        return diamondCriteriaResponseDTO;
    }
}
