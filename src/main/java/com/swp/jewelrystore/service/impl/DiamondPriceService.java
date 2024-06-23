package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.model.dto.DiamondCriteriaDTO;
import com.swp.jewelrystore.model.dto.GemWithPriceDTO;
import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;
import com.swp.jewelrystore.repository.GemPriceRepository;
import com.swp.jewelrystore.repository.GemRepository;
import com.swp.jewelrystore.service.IDiamondPriceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiamondPriceService implements IDiamondPriceService {

    private final GemPriceRepository gemPriceRepository;
    private final GemRepository gemRepository;
    private final ModelMapper modelMapper;
    private final DateTimeConverter dateTimeConverter;

    @Override
    public List<DiamondResponseDTO> getDiamondPrice() {
        List<DiamondResponseDTO> result = new ArrayList<>();
        List<GemEntity> listGem = gemRepository.findAll();
        for (GemEntity gem : listGem) {
            GemPriceEntity gemPrice = gemPriceRepository.findLatestGemPrice(gem);
            DiamondResponseDTO diamond = modelMapper.map(gemPrice, DiamondResponseDTO.class);
            diamond.setId(gem.getId());
            diamond.setName(gem.getGemName());
            diamond.setEffectDate(dateTimeConverter.convertToDateTimeResponse(gemPrice.getEffectDate()));
            result.add(diamond);
        }
        return result;
    }

    @Override
    public void addOrUpdateDiamondPrice(GemWithPriceDTO gem) {
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
}
