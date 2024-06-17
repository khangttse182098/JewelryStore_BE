package com.swp.jewelrystore.model.dto;

import com.swp.jewelrystore.model.response.CriteriaResponseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Valid
public class PurchaseOrderDTO {

    @ApiModelProperty(example = "Hoàng Nguyên Khang")
    private String fullName;

    @ApiModelProperty(example = "123 Lý Thuong Kiet")
    private String address;

    @NotEmpty(message="Phone number is required")
    @ApiModelProperty(example = "0955926424")
    private String phoneNumber;

    @NotNull(message="User id can not empty or null")
    @ApiModelProperty(example = "4")
    private Long userId;

    @NotNull(message="Material or gem criteria are required")
    private CriteriaResponseDTO criteria;

}
