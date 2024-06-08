package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiamondCriteriaDTO {

    @ApiModelProperty(example = "Tự nhiên")
    private String origin;

    @ApiModelProperty(example = "D")
    private String color;

    @ApiModelProperty(example = "VVS2")
    private String clarity;

    @ApiModelProperty(example = "1")
    private Double caratWeight;

    @ApiModelProperty(example = "GD")
    private String cut;
}
