package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;
import com.swp.jewelrystore.model.dto.DiamondCriteriaDTO;


public interface GemPriceRepositoryCustom {
    GemPriceEntity findLatestGemPrice(GemEntity gemEntity);
    GemPriceEntity checkGemCaratInRange(DiamondCriteriaDTO diamondCriteriaDTO);
}
