package com.swp.jewelrystore.api;

import com.swp.jewelrystore.entity.MaterialEntity;
import com.swp.jewelrystore.model.dto.MaterialDTO;
import com.swp.jewelrystore.model.response.MaterialDropDownResponseDTO;
import com.swp.jewelrystore.model.response.MaterialResponseDTO;
import com.swp.jewelrystore.model.response.ProductResponseDTO;
import com.swp.jewelrystore.repository.MaterialRepository;
import com.swp.jewelrystore.service.impl.MaterialService;
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
    @Autowired
    private MaterialService materialService;

    @GetMapping
    public List<MaterialDropDownResponseDTO> getMaterial(){
        List<MaterialDropDownResponseDTO> materialDropDownResponseDTOS = new ArrayList<MaterialDropDownResponseDTO>();
        List<MaterialEntity> materialEntities = materialRepository.findAll();
        for (MaterialEntity materialEntity : materialEntities) {
            materialDropDownResponseDTOS.add(modelMapper.map(materialEntity, MaterialDropDownResponseDTO.class));
        }
        return materialDropDownResponseDTOS;
    }
    @PostMapping
    public String addOrUpdateMaterial(@RequestBody MaterialDTO materialDTO){
        materialService.addOrUpdateMaterial(materialDTO);
        if(materialDTO.getMaterialId() == null) return  "Add product successfully";
        else return  "Update product successfully";
    }
}
