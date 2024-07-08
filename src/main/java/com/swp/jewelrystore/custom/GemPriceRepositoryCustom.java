package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.model.dto.DiamondCriteriaDTO;
import com.swp.jewelrystore.model.dto.GemKeyDTO;
import com.swp.jewelrystore.model.response.GemPriceResponseDTO;


import java.util.List;


public interface GemPriceRepositoryCustom {
    GemPriceEntity findLatestGemPrice(GemEntity gemEntity);
    GemPriceEntity checkGemCaratInRange(DiamondCriteriaDTO diamondCriteriaDTO);
    List<GemPriceEntity> findAllGemPriceHistory(GemKeyDTO gemKey);
    String autoGenerateCode();
    List<GemPriceResponseDTO> getGemDistinct();
    GemPriceEntity getGemDistinctInformation(GemPriceResponseDTO gemPriceResponseDTO);
    List<GemPriceEntity> getGemExistedWithoutDate(DiamondCriteriaDTO diamondCriteriaDTO);
    GemPriceEntity getGemExistedWithHigherDate(GemPriceResponseDTO gemPriceResponseDTO);
}
