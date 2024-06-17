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
    private Long id;

    @NotNull(message="Value of discount is required")
    @JsonProperty("value")
    private Long value;

    @NotEmpty(message="Code is required")
    @JsonProperty("code")
    private String code;

    @NotEmpty(message="Start date of discount is required")
    @JsonProperty("startDate")
    private String startDateDTO;

    @NotEmpty(message="End date of discount is required")
    @JsonProperty("endDate")
    private String endDateDTO;
}
