package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.DiscountEntity;

import java.util.List;
import java.util.Map;

public interface DiscountRepositoryCustom {
    List<DiscountEntity>  searchWithRequired(Map<String, String> filter);
    DiscountEntity findApplyingDiscount();
}
