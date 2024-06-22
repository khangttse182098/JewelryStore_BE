package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.GemWithPriceDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;
import com.swp.jewelrystore.model.response.GemResponseDTO;

import java.util.List;

public interface IGemService {
    List<GemResponseDTO> getAllGem();
    GemDetailResponseDTO getGemDetail(Long id);
    void addOrUpdateGemWithPrice(GemWithPriceDTO gem);
}
