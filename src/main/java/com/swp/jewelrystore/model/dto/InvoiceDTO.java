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

    @NotNull(message="Danh sách id của sản phẩm không được trống")
    @ApiModelProperty(example = "[1, 2]")
    private List<Long> productId;

    @NotBlank(message="Tên khách hàng không được để trống")
    @ApiModelProperty(example = "Dương Quốc Hoàng")
    private String fullName;

    @NotNull(message="Số điện thoại không được để trống")
    @ApiModelProperty(example = "0686888990")
    private String phoneNumber;

    @ApiModelProperty(example = "tim em")
    private String address;

    @NotNull(message="Id của user không được để trống")
    @ApiModelProperty(example = "1")
    private Long userId;

    @NotNull(message="Danh sách giá sản phẩm không được để trống và phải theo thứ tự")
    @ApiModelProperty(example = "[92714, 89660]")
    private List<Double> price;

    @NotNull(message="Id của mã giảm giá không được để trống")
    @ApiModelProperty(example = "1")
    private Long discountId;

}
