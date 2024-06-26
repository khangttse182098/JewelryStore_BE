package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiamondResponseDTO {
    private Long id;
    private String gemCode;
    private String gemName;
    private String origin;
    private String clarity;
    private String cut;
    private String caratWeight;
    private String color;
    private Double sellPrice;
    private String proportions;
    private String polish;
    private String symmetry;
    private String fluorescence;
}
