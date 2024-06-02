package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CriteriaDTO {
    private List<GoldCriteriaDTO> listGoldCriteria;
    private List<DiamondCriteriaDTO> listDiamondCriteria;
}
