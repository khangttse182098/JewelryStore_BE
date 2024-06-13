package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InvoiceDTO {

    @ApiModelProperty(example = "[1, 2]")
    private List<Long> productId;

    @ApiModelProperty(example = "Dương Quốc Hoàng")
    private String fullName;

    @ApiModelProperty(example = "0686888990")
    private String phoneNumber;

    @ApiModelProperty(example = "tim em")
    private String address;

    @ApiModelProperty(example = "1")
    private Long userId;

    @ApiModelProperty(example = "Chưa thanh toán")
    private String sellOrderStatus;

    @ApiModelProperty(example = "[92714, 89660]")
    private List<Double> price;

    @ApiModelProperty(example = "1")
    private Long discountId;

}
