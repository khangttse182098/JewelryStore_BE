package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.custom.GemPriceRepositoryCustom;
import com.swp.jewelrystore.entity.GemPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GemPriceRepository extends JpaRepository<GemPriceEntity, Long>, GemPriceRepositoryCustom {

}
