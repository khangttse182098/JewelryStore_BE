package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.GemEntity;
import com.swp.jewelrystore.entity.GemPriceEntity;


public interface GemPriceRepositoryCustom {
    GemPriceEntity findLatestGemPrice(GemEntity gemEntity);
}
