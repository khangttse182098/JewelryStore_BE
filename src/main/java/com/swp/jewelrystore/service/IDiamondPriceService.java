package com.swp.jewelrystore.service;


import com.swp.jewelrystore.model.dto.DiamondDTO;
import com.swp.jewelrystore.model.dto.GemKeyDTO;
import com.swp.jewelrystore.model.dto.GemPriceDTO;
import com.swp.jewelrystore.model.dto.GemWithPriceDTO;
import com.swp.jewelrystore.model.response.DiamondResponseDTO;
import com.swp.jewelrystore.model.response.GemDetailResponseDTO;
import com.swp.jewelrystore.model.response.GemPriceDistinctResponseDTO;


import java.util.List;

public interface IDiamondPriceService {
    List<DiamondResponseDTO> getDiamondInformation();
    void addOrUpdateDiamondPrice(GemWithPriceDTO gem);
    GemDetailResponseDTO getDiamondDetail(Long id);
    List<GemPriceDistinctResponseDTO> getHistoryGemPrice();
    String addDiamondEntity(DiamondDTO diamondDTO);
    void addNewPriceForDiamondEntity(GemPriceDTO gemPriceDTO);
    List<GemPriceDistinctResponseDTO> getHistoryGemPriceDetail(GemKeyDTO gemKey);
}
