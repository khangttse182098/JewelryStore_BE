package com.swp.jewelrystore.api;

import com.swp.jewelrystore.entity.MaterialEntity;
import com.swp.jewelrystore.model.response.MaterialDropDownResponseDTO;
import com.swp.jewelrystore.repository.MaterialRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/material")
@CrossOrigin
@RequiredArgsConstructor
public class MaterialAPI {

    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;


    @GetMapping
    public List<MaterialDropDownResponseDTO> getMaterial(){
        List<MaterialDropDownResponseDTO> materialDropDownResponseDTOS = new ArrayList<MaterialDropDownResponseDTO>();
        List<MaterialEntity> materialEntities = materialRepository.findAll();
        for (MaterialEntity materialEntity : materialEntities) {
            materialDropDownResponseDTOS.add(modelMapper.map(materialEntity, MaterialDropDownResponseDTO.class));
        }
        return materialDropDownResponseDTOS;
    }

}
