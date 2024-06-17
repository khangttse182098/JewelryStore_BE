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
public class DiscountDTO {

    @JsonProperty("id")
    @ApiModelProperty(example="")
    private Long id;

    @NotNull(message="Value of discount is required")
    @JsonProperty("value")
    @ApiModelProperty(example="45")
    private Long value;

    @NotEmpty(message="Code is required")
    @JsonProperty("code")
    @ApiModelProperty(example="DISCOUNT45")
    private String code;

    @NotEmpty(message="Start date of discount is required")
    @JsonProperty("startDate")
    @ApiModelProperty(example="17/06/2024 20:05:00")
    private String startDateDTO;

    @NotEmpty(message="End date of discount is required")
    @JsonProperty("endDate")
    @ApiModelProperty(example="17/12/2024 20:05:00")
    private String endDateDTO;
}
