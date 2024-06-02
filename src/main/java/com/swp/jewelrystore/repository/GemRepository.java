package com.swp.jewelrystore.repository;

import com.swp.jewelrystore.entity.GemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GemRepository extends JpaRepository<GemEntity, Long> {
    GemEntity findByOriginAndColorAndClarityAndCaratWeightAndCut(String origin, String color, String clarity, Double caratWeight, String cut);
    GemEntity findByGemName(String gemName);
}
