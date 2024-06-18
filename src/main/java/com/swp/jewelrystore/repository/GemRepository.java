package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.GemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GemRepository extends JpaRepository<GemEntity, Long> {
    GemEntity findByOriginAndColorAndClarityAndCaratWeightAndCut(String origin, String color, String clarity, Double caratWeight, String cut);
    GemEntity findByGemName(String gemName);
    GemEntity findGemEntityById(long id);
    List<GemEntity> findGemEntityByActive(Long active);
}
