package com.swp.jewelrystore.api;

import com.swp.jewelrystore.entity.MaterialEntity;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.repository.MaterialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/material")
@CrossOrigin
public class MaterialAPI {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<MaterialResponseDTO> getMaterial(){
        List<MaterialResponseDTO> materialResponseDTOList = new ArrayList<MaterialResponseDTO>();
        List<MaterialEntity> materialEntities = materialRepository.findAll();
        for (MaterialEntity materialEntity : materialEntities) {
            materialResponseDTOList.add(modelMapper.map(materialEntity, MaterialResponseDTO.class));
        }
        return materialResponseDTOList;
    }
}
