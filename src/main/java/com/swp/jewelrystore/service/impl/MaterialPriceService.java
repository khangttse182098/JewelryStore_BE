package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.entity.MaterialEntity;
import com.swp.jewelrystore.entity.MaterialPriceEntity;
import com.swp.jewelrystore.model.dto.MaterialPriceDTO;
import com.swp.jewelrystore.repository.MaterialPriceRepository;
import com.swp.jewelrystore.repository.MaterialRepository;
import com.swp.jewelrystore.service.IMaterialPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialPriceService implements IMaterialPriceService {
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private MaterialPriceRepository materialPriceRepository;

    @Override
    public void addOrUpdateMaterialPrice(MaterialPriceDTO materialPriceDTO) {
        MaterialEntity materialEntity = materialRepository.findMaterialEntityById(materialPriceDTO.getMaterialId());
        MaterialPriceEntity materialPriceEntity = new MaterialPriceEntity();
        materialPriceEntity.setMaterial(materialEntity);
        materialPriceEntity.setBuyPrice(materialPriceDTO.getBuyPrice());
        materialPriceEntity.setSellPrice(materialPriceDTO.getSellPrice());
        materialPriceEntity.setEffectDate(materialPriceDTO.getEffectDate());
        materialPriceRepository.save(materialPriceEntity);
    }
}
