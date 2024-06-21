package com.swp.jewelrystore.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Valid
public class DiscountDTO {

    @JsonProperty("id")
    @ApiModelProperty(example="")
    private Long id;

    @NotNull(message="Giá trị không được trống")
    @JsonProperty("value")
    @ApiModelProperty(example="45")
    @Min(value = 1, message = "Giá trị không thể bé hơn 1")
    @Max(value = 100, message = "Giá trị không thể lớn hơn 100")
    private Long value;

    @NotEmpty(message="Mã giảm giá không thể trống")
    @JsonProperty("code")
    @ApiModelProperty(example="DISCOUNT45")
    private String code;

    @NotEmpty(message="Ngày bắt đầu không được trống")
    @JsonProperty("startDate")
    @ApiModelProperty(example="17/06/2024 20:05")
    private String startDateDTO;

    @NotEmpty(message="Ngày kết thúc không được trống")
    @JsonProperty("endDate")
    @ApiModelProperty(example="17/12/2024 20:05")
    private String endDateDTO;
}
