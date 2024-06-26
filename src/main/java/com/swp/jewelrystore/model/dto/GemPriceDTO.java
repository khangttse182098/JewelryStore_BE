package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

@Getter
@Setter
@Valid
public class GemPriceDTO {
    private String origin;
    private String color;
    private String clarity;
    private String cut;
    private Double caratWeightFrom;
    private Double caratWeightTo;
    private Double sellPrice;
    private Double buyPrice;
    private String effectDate;
}
