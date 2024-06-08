package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.repository.GemPriceRepository;
import com.swp.jewelrystore.repository.GemRepository;
import com.swp.jewelrystore.service.IDiamondPriceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
            diamond.setName(gem.getGemName());
            diamond.setEffectDate(dateTimeConverter.convertToDateTimeResponse(gemPrice.getEffectDate()));
            result.add(diamond);
        }
        return result;
    }
}
