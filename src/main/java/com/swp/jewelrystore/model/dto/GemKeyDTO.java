package com.swp.jewelrystore.model.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GemKeyDTO {
    private String origin;
    private String color;
    private String clarity;
    private String cut;
    private Double caratWeightFrom;
    private Double caratWeightTo;
}
