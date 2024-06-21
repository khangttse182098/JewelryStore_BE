package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.DateTimeConverter;
import com.swp.jewelrystore.customexception.MaterialException;
import com.swp.jewelrystore.entity.MaterialEntity;
import com.swp.jewelrystore.entity.MaterialPriceEntity;
import com.swp.jewelrystore.model.dto.MaterialPriceDTO;
import com.swp.jewelrystore.repository.MaterialPriceRepository;
import com.swp.jewelrystore.repository.MaterialRepository;
import com.swp.jewelrystore.service.IMaterialPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaterialPriceService implements IMaterialPriceService {

    private final MaterialRepository materialRepository;
    private final MaterialPriceRepository materialPriceRepository;
    private final DateTimeConverter dateTimeConverter;

    @Override
    public void addOrUpdateMaterialPrice(MaterialPriceDTO materialPriceDTO) {
        // có id
        if (materialPriceDTO.getMaterialId() != null) {
            MaterialEntity materialEntity = materialRepository.findMaterialEntityById(materialPriceDTO.getMaterialId());
            MaterialPriceEntity materialPriceEntity = new MaterialPriceEntity();
            materialPriceEntity.setMaterial(materialEntity);
            materialPriceEntity.setBuyPrice(materialPriceDTO.getBuyPrice());
            materialPriceEntity.setSellPrice(materialPriceDTO.getSellPrice());
            materialPriceEntity.setEffectDate(dateTimeConverter.convertToDateTimeDTO(materialPriceDTO.getEffectDate()));
            materialPriceRepository.save(materialPriceEntity);
        } else {
            MaterialEntity existedName = materialRepository.findByNameLike(materialPriceDTO.getGoldName());
            // exited MaterialEntity
            if (existedName != null) {
                 throw new MaterialException("Tên loại vàng đã tồn tại ! Vui lòng chọn tên khác");
            } else if (materialPriceDTO.getBuyPrice().isNaN()){
                 throw new MaterialException("Giá mua của loại vàng phải ở dạng số, không thể để trống");
            } else if (materialPriceDTO.getSellPrice().isNaN()){
                throw new MaterialException("Giá bán của loại vàng phải ở dạng số, không thể để trống");
            } else if (materialPriceDTO.getEffectDate().isEmpty()){
                throw new MaterialException("Ngày hiệu lực của giá vàng không thể để trống");
            }
            MaterialEntity materialEntity = new MaterialEntity();
            materialEntity.setName(materialPriceDTO.getGoldName());
            materialRepository.save(materialEntity);
            MaterialPriceEntity materialPriceEntity = new MaterialPriceEntity();
            materialPriceEntity.setMaterial(materialEntity);
            materialPriceEntity.setBuyPrice(materialPriceDTO.getBuyPrice());
            materialPriceEntity.setSellPrice(materialPriceDTO.getSellPrice());
            materialPriceEntity.setEffectDate(dateTimeConverter.convertToDateTimeDTO(materialPriceDTO.getEffectDate()));
            materialPriceRepository.save(materialPriceEntity);
        }

    }
}
