package com.swp.jewelrystore.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example="")
    private Long id;

    @NotEmpty(message="Origin can not empty or null")
    @JsonProperty("origin")
    @ApiModelProperty(example="Nhân tạo")
    private String origin;

    @NotEmpty(message="Color is required")
    @JsonProperty("color")
    @ApiModelProperty(example="H")
    private String color;

    @NotEmpty(message="Clarity is required")
    @JsonProperty("clarity")
    @ApiModelProperty(example="VVS1")
    private String clarity;

    @NotNull(message="Carat weight from can not null or empty and bigger than 0")
    @JsonProperty("caratWeightFrom")
    @ApiModelProperty(example="2.3")
    private Double caratWeightFrom;

    @NotNull(message="Carat weight to can not null or empty")
    @JsonProperty("caratWeightTo")
    @ApiModelProperty(example="2.65")
    private Double caratWeightTo;

    @NotEmpty(message="Cut is required")
    @JsonProperty("cut")
    @ApiModelProperty(example="PR")
    private String cut;

    @NotNull(message="Buy price can not null or empty")
    @JsonProperty("buyPrice")
    @ApiModelProperty(example="25000000")
    private Double buyPrice;

    @NotNull(message="Sell price can not null or empty")
    @JsonProperty("sellPrice")
    @ApiModelProperty(example="27250000")
    private Double sellPrice;

    @NotEmpty(message="Effect date is required")
    @JsonProperty("effectDate")
    @ApiModelProperty(example="20/06/2024 12:00:00")
    private String effecttDate;
}
