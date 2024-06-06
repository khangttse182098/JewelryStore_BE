package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.MaterialPriceDTO;

public interface IMaterialPriceService {
    void addOrUpdateMaterialPrice(MaterialPriceDTO materialPriceDTO);
}
