package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusDTO {
    @ApiModelProperty(example = "PURC1")
    private String invoiceCode;
}
