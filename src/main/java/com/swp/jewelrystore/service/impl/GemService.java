package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.model.response.GemResponseDTO;
import com.swp.jewelrystore.repository.GemRepository;
import com.swp.jewelrystore.service.IGemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class GemService implements IGemService {
    @Autowired
    private GemRepository gemRepository;

    @Override
    public List<GemResponseDTO> getAllGem() {
        List<GemEntity> gemEntities = gemRepository.findGemEntityByActive(1L);

        return Collections.emptyList();
    }
}
