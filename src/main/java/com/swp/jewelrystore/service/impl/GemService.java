package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.model.response.GemResponseDTO;
import com.swp.jewelrystore.repository.GemRepository;
import com.swp.jewelrystore.service.IGemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class GemService implements IGemService {
    @Autowired
    private GemRepository gemRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GemResponseDTO> getAllGem() {
        List<GemResponseDTO> gemResponseDTOS = new ArrayList<>();
        List<GemEntity> gemEntities = gemRepository.findGemEntityByActive(1L);
        for (GemEntity gemEntity : gemEntities) {
            GemResponseDTO gemResponseDTO = modelMapper.map(gemEntity, GemResponseDTO.class);
            gemResponseDTOS.add(gemResponseDTO);
        }
        return gemResponseDTOS;
    }
}
