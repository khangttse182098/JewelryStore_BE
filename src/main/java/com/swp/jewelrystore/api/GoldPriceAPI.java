package com.swp.jewelrystore.api;


import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.entity.MaterialEntity;
import com.swp.jewelrystore.entity.MaterialPriceEntity;
import com.swp.jewelrystore.model.dto.MaterialPriceDTO;
import com.swp.jewelrystore.model.response.GoldResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.repository.MaterialRepository;
import com.swp.jewelrystore.service.IGoldPriceService;
import com.swp.jewelrystore.service.impl.MaterialPriceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/gold-price")
@CrossOrigin
@RequiredArgsConstructor
public class GoldPriceAPI {

    private final IGoldPriceService goldPriceService;
    private final MaterialRepository materialRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private MaterialPriceService materialPriceService;
    @Autowired
    private DateTimeConverter dateTimeConverter;
    @GetMapping
      public List<GoldResponseDTO> goldPriceList(){
          return goldPriceService.goldPriceList();
      }

      @GetMapping("/{id}")
    public List<GoldResponseDTO> getListPriceByMaterialId(@PathVariable Long id){
          MaterialEntity materialEntity = materialRepository.findMaterialEntityById(id);
          List<MaterialPriceEntity> materialPriceEntities =  materialEntity.getMaterialPriceEntities();
          List<GoldResponseDTO> goldResponseDTOList = new ArrayList<>();
          for(MaterialPriceEntity materialPriceEntity : materialPriceEntities){
              GoldResponseDTO goldResponseDTO = modelMapper.map(materialPriceEntity, GoldResponseDTO.class);
              goldResponseDTO.setGoldName(materialEntity.getName());
              System.out.println(materialPriceEntity.getEffectDate().toString());
              goldResponseDTO.setEffectDate(dateTimeConverter.convertToDateTimeResponse(materialPriceEntity.getEffectDate()));
              goldResponseDTOList.add(goldResponseDTO);
          }
          Collections.reverse(goldResponseDTOList);
          return goldResponseDTOList;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addOrUpdateGoldPrice(@Valid @RequestBody MaterialPriceDTO materialPriceDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            materialPriceService.addOrUpdateMaterialPrice(materialPriceDTO);
            if(materialPriceDTO.getMaterialId() != null){
                responseDTO.setMessage("Update material price successfully");
                return ResponseEntity.ok(responseDTO);
            }
            responseDTO.setMessage("Add marterial price successfully");
            responseDTO.setData(materialPriceDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e){
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }
}
