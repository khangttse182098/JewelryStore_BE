package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.entity.PurchaseOrderDetailEntity;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MaterialConverter {
    @Autowired
    private ModelMapper modelMapper;

    public MaterialResponseDTO toMaterialResponseDTO(PurchaseOrderDetailEntity purchaseOrderDetailEntity){
        MaterialResponseDTO materialResponseDTO = modelMapper.map(purchaseOrderDetailEntity, MaterialResponseDTO.class);
        return materialResponseDTO;
    }

}
