package com.swp.jewelrystore.custom;

import com.swp.jewelrystore.entity.MaterialEntity;
import com.swp.jewelrystore.entity.MaterialPriceEntity;
import com.swp.jewelrystore.entity.ProductEntity;
import org.springframework.stereotype.Service;


public interface MaterialPriceRepositoryCustom {
    MaterialPriceEntity findLatestMaterialPrice(ProductEntity productEntity);
    MaterialPriceEntity findLatestGoldPrice(MaterialEntity materialEntity);
}
