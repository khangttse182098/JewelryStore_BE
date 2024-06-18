package com.swp.jewelrystore.converter;

import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.PurchaseOrderDetailEntity;
import com.swp.jewelrystore.model.response.GemResponseDTO;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GemConverter {
    @Autowired
    private ModelMapper modelMapper;

    public GemResponseDTO toGemResponseDTO(GemEntity gemEntity){
        GemResponseDTO gemResponseDTO = modelMapper.map(gemEntity, GemResponseDTO.class);
        return gemResponseDTO;
    }
}
