package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiamondCriteriaDTO {
    private String origin;
    private String color;
    private String clarity;
    private Double caratWeight;
    private String cut;
}
