package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.model.dto.DiamondCriteriaDTO;
import com.swp.jewelrystore.model.dto.GemWithPriceDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;
import com.swp.jewelrystore.model.response.GemForProductResponseDTO;
import com.swp.jewelrystore.model.response.GemResponseDTO;
import com.swp.jewelrystore.repository.GemPriceRepository;
import com.swp.jewelrystore.repository.GemRepository;
import com.swp.jewelrystore.service.IGemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
@RequiredArgsConstructor
public class GemService implements IGemService {
    private final GemPriceRepository gemPriceRepository;
    private final GemRepository gemRepository;
    private final ModelMapper modelMapper;
    private final DateTimeConverter dateTimeConverter;

    @Override
    public List<GemResponseDTO> getAllGem() {
        List<GemResponseDTO> gemResponseDTOS = new ArrayList<>();
        List<GemEntity> gemEntities = gemRepository.findGemEntityByActive(1L);
        for (GemEntity gemEntity : gemEntities) {
            GemResponseDTO gemResponseDTO = modelMapper.map(gemEntity, GemResponseDTO.class);
            GemPriceEntity gemPrice = gemPriceRepository.findLatestGemPrice(gemEntity);
            gemResponseDTO.setEffectDate(dateTimeConverter.convertToDateTimeResponse(gemPrice.getEffectDate()));
            gemResponseDTO.setBuyPrice(gemPrice.getBuyPrice());
            gemResponseDTO.setSellPrice(gemPrice.getSellPrice());
            gemResponseDTOS.add(gemResponseDTO);
        }
        return gemResponseDTOS;
    }

    @Override
    public List<GemForProductResponseDTO> getAllGemForProduct() {
        List<GemForProductResponseDTO> gemForProductResponseDTOS = new ArrayList<>();
        List<GemEntity> gemEntities = gemRepository.findGemEntityByActive(1L);
        for (GemEntity gemEntity : gemEntities) {
            GemForProductResponseDTO gemForProductResponseDTO = modelMapper.map(gemEntity, GemForProductResponseDTO.class);
            gemForProductResponseDTOS.add(gemForProductResponseDTO);
        }
        return gemForProductResponseDTOS;
    }

    @Override
    public GemDetailResponseDTO getGemDetail(Long id) {
        GemEntity gemEntity = gemRepository.findGemEntityById(id);
        GemDetailResponseDTO gemDetail = modelMapper.map(gemEntity, GemDetailResponseDTO.class);
        GemPriceEntity gemPrice = gemPriceRepository.findLatestGemPrice(gemEntity);
        gemDetail.setEffectDate(dateTimeConverter.convertToDateTimeResponse(gemPrice.getEffectDate()));
        gemDetail.setBuyPrice(gemPrice.getBuyPrice());
        gemDetail.setSellPrice(gemPrice.getSellPrice());
        return gemDetail;
    }

    @Override
    public void addOrUpdateGemWithPrice(GemWithPriceDTO gem) {
        GemEntity gemEntity = gemRepository.findById(gem.getGemId()).orElse(null);
        // using condition in modelMapper to avoid id
        PropertyMap<GemPriceEntity, GemPriceEntity> gemPriceMap = new PropertyMap<GemPriceEntity, GemPriceEntity>() {
            protected void configure() {
                skip(destination.getId());
            }
        };
         modelMapper.addMappings(gemPriceMap);
         if (gemEntity == null) {

         } else {
             // change diamond name
             gemEntity.setGemName(gem.getDiamondName());
             gemRepository.save(gemEntity);
             DiamondCriteriaDTO diamondCriteria = modelMapper.map(gemEntity, DiamondCriteriaDTO.class);
             GemPriceEntity gemPriceEntity = gemPriceRepository.checkGemCaratInRange(diamondCriteria);
             GemPriceEntity newRecordForGem = modelMapper.map(gemPriceEntity, GemPriceEntity.class);
             newRecordForGem.setBuyPrice(gem.getBuyPrice());
             newRecordForGem.setSellPrice(gem.getSellPrice());
             newRecordForGem.setEffectDate(dateTimeConverter.convertToDateTimeDTO(gem.getEffectDate()));
             gemPriceRepository.save(newRecordForGem);
         }
    }
}
