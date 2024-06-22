package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.model.response.GemResponseDTO;
import com.swp.jewelrystore.repository.GemPriceRepository;
import com.swp.jewelrystore.repository.GemRepository;
import com.swp.jewelrystore.service.IGemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

}
