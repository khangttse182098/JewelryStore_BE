package com.swp.jewelrystore.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Valid
public class InvoiceDTO {

    @NotNull(message="Product id are required")
    @ApiModelProperty(example = "[1, 2]")
    private List<Long> productId;

    @NotBlank(message="Customer name can not contain blank")
    @ApiModelProperty(example = "Dương Quốc Hoàng")
    private String fullName;

    @NotNull(message="Phone number can not null")
    @ApiModelProperty(example = "0686888990")
    private String phoneNumber;

    @ApiModelProperty(example = "tim em")
    private String address;

    @NotNull(message="User id is required")
    @ApiModelProperty(example = "1")
    private Long userId;

    @NotNull(message="Price of products can not null or empty and must be in order")
    @ApiModelProperty(example = "[92714, 89660]")
    private List<Double> price;

    @NotNull(message="Discount id can not empty or null")
    @ApiModelProperty(example = "1")
    private Long discountId;

}
