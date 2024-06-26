package com.swp.jewelrystore.model.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GemPriceDistinctResponseDTO {
    private String origin;
    private String color;
    private String clarity;
    private String cut;
    private Double caratWeightFrom;
    private Double caratWeightTo;
    private Double buyPrice;
    private Double sellPrice;
    private String effectDate;
}
