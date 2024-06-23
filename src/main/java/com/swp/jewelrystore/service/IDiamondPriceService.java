package com.swp.jewelrystore.service;


import com.swp.jewelrystore.model.dto.GemWithPriceDTO;
import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;

import java.util.List;

public interface IDiamondPriceService {
    List<DiamondResponseDTO> getDiamondPrice();
    void addOrUpdateDiamondPrice(GemWithPriceDTO gem);
    GemDetailResponseDTO getGemDetail(Long id);
    List<DiamondResponseDTO> getHistoryGemPrice(Long id);
}
