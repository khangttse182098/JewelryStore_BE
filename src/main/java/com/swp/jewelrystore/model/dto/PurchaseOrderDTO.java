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

    @NotEmpty(message="Số điện thoại không được để trống")
    @ApiModelProperty(example = "0955926424")
    private String phoneNumber;

    @NotNull(message="Id của user không được để trống")
    @ApiModelProperty(example = "4")
    private Long userId;

    @NotNull(message="Tiêu chí về vàng hoặc đá phải có")
    private CriteriaResponseDTO criteria;

}
