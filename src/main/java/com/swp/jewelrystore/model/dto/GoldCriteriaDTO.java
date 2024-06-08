package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoldCriteriaDTO {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Vàng miếng SJC 999.9")
    private String name;

    @ApiModelProperty(example = "2")
    private Double weight;
}
