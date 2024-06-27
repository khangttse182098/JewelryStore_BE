package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Valid
public class PurchaseInvoiceDTO {

    @ApiModelProperty(value="No need userId if just showing price", example = "3")
    private Long userId;

    @NotNull(message = "Product id is required")
    @ApiModelProperty(example = "[2]")
    private List<Long> productId;

    @NotEmpty(message = "Sell order code is required")
    @ApiModelProperty(value="No need sellOrderCode if just showing price", example = "SELL1")
    private String sellOrderCode;
}
