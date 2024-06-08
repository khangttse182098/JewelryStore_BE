package com.swp.jewelrystore.model.dto;

import com.swp.jewelrystore.model.response.CriteriaResponseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseOrderDTO {

    @ApiModelProperty(example = "Hoàng Nguyên Khang")
    private String fullName;

    @ApiModelProperty(example = "123 Lý Thuong Kiet")
    private String address;

    @ApiModelProperty(example = "0955926424")
    private String phoneNumber;

    @ApiModelProperty(example = "4")
    private Long userId;

    private CriteriaResponseDTO criteria;

    @ApiModelProperty(example = "Chưa thanh toán")
    private String purchaseOrderStatus;
}
