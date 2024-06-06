package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.MaterialEntity;
import com.swp.jewelrystore.entity.MaterialPriceEntity;
import com.swp.jewelrystore.model.dto.MaterialDTO;
import com.swp.jewelrystore.repository.MaterialPriceRepository;
import com.swp.jewelrystore.repository.MaterialRepository;
import com.swp.jewelrystore.service.IMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialService implements IMaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialPriceRepository materialPriceRepository;

    @Override
    public void addOrUpdateMaterial(MaterialDTO materialDTO) {
        MaterialEntity materialEntity = materialRepository.findMaterialEntityById(materialDTO.getMaterialId());
        MaterialPriceEntity materialPriceEntity = new MaterialPriceEntity();
        materialPriceEntity.setMaterial(materialEntity);
        materialPriceEntity.setBuyPrice(materialDTO.getBuyPrice());
        materialPriceEntity.setSellPrice(materialDTO.getSellPrice());
        materialPriceEntity.setEffectDate(materialDTO.getEffectDate());
        materialPriceRepository.save(materialPriceEntity);
    }
}
