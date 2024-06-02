package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.MaterialPriceRepositoryCustom;
import com.swp.jewelrystore.entity.MaterialPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public interface MaterialPriceRepository extends JpaRepository<MaterialPriceEntity, Long>, MaterialPriceRepositoryCustom {
}
