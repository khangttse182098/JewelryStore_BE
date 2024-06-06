package com.swp.jewelrystore.api;


import com.swp.jewelrystore.entity.MaterialEntity;
import com.swp.jewelrystore.entity.MaterialPriceEntity;
import com.swp.jewelrystore.model.response.GoldResponseDTO;
import com.swp.jewelrystore.repository.MaterialRepository;
import com.swp.jewelrystore.service.IGoldPriceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/gold-price")
@CrossOrigin
@RequiredArgsConstructor
public class GoldPriceAPI {

    private final IGoldPriceService goldPriceService;
    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;

    @GetMapping
      public List<GoldResponseDTO> goldPriceList(){
          return goldPriceService.goldPriceList();
      }

      @GetMapping("/{id}")
    public List<GoldResponseDTO> getPriceByMaterialId(@PathVariable Long id){
          MaterialEntity materialEntity = materialRepository.findMaterialEntityById(id);
          List<MaterialPriceEntity> materialPriceEntities =  materialEntity.getMaterialPriceEntities();
          List<GoldResponseDTO> goldResponseDTOList = new ArrayList<>();
          for(MaterialPriceEntity materialPriceEntity : materialPriceEntities){
              GoldResponseDTO goldResponseDTO = modelMapper.map(materialPriceEntity, GoldResponseDTO.class);
              goldResponseDTO.setGoldName(materialEntity.getName());
              goldResponseDTOList.add(goldResponseDTO);
          }
          return goldResponseDTOList;
    }
}
