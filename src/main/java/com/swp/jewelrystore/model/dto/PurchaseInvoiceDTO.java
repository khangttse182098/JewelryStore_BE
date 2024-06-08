package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseInvoiceDTO {

    @ApiModelProperty(value="No need userId if just showing price", example = "3")
    private Long userId;

    @ApiModelProperty(example = "[1, 2]")
    private List<Long> productId;

    @ApiModelProperty(value="No need sellOrderCode if just showing price", example = "SELL1")
    private String sellOrderCode;
}
