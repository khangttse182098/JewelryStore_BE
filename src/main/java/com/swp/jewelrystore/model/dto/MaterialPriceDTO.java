package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
@Getter
@Setter
@Valid
public class MaterialPriceDTO {

    @ApiModelProperty(example = "1")
    private Long materialId;

    @NotNull(message = "Buy price is required")
    @ApiModelProperty(example = "7799000")
    private Double buyPrice;

    @NotNull(message = "Sell price is required")
    @ApiModelProperty(example = "8080000")
    private Double sellPrice;

    @NotEmpty(message = "Effect date is required")
    @ApiModelProperty(example = "06/06/2024 00:00")
    private String effectDate;
    @ApiModelProperty(example = "Vàng đầu khấc")
    private String goldName;
}
