package com.swp.jewelrystore.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GemKeyDTO {
    private String origin;
    private String color;
    private String clarity;
    private String cut;
    private Double caratWeightFrom;
    private Double caratWeightTo;
}
