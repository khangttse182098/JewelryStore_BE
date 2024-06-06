package com.swp.jewelrystore.model.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiamondCriteriaResponseDTO {
    private String origin;
    private String color;
    private String clarity;
    private Double caratWeight;
    private String cut;
    private Double price;
}
