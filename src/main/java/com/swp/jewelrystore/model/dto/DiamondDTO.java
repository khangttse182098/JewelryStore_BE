package com.swp.jewelrystore.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Valid
public class DiamondDTO {

    @JsonProperty("id")
    private Long id;

    @NotEmpty(message="Origin can not empty or null")
    @JsonProperty("origin")
    private String origin;

    @NotEmpty(message="Color is required")
    @JsonProperty("color")
    private String color;

    @NotEmpty(message="Clarity is required")
    @JsonProperty("clarity")
    private String clarity;

    @NotNull(message="Carat weight from can not null or empty and bigger than 0")
    @JsonProperty("caratWeightFrom")
    private Double caratWeightFrom;

    @NotNull(message="Carat weight to can not null or empty")
    @JsonProperty("caratWeightTo")
    private Double caratWeightTo;

    @NotEmpty(message="Cut is required")
    @JsonProperty("cut")
    private String cut;

    @NotNull(message="Buy price can not null or empty")
    @JsonProperty("buyPrice")
    private Double buyPrice;

    @NotNull(message="Sell price can not null or empty")
    @JsonProperty("sellPrice")
    private Double sellPrice;

    @NotEmpty(message="Effect date is required")
    @JsonProperty("effectDate")
    private String effecttDate;
}
