package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Valid
public class CustomerDTO {
    @ApiModelProperty(example = "xem model",  value = "sửa id thành \"\" nếu muốn thêm kh, sửa id thành \"3\" nếu muốn update kh có id = 3")
    private Long id;

    @NotEmpty(message = "Phone number is required")
    @ApiModelProperty(example = "0907054032")
    private String phoneNumber;

    @NotEmpty(message = "Full name is required")
    @ApiModelProperty(example = "Pham Hoang Phuc Dep Trai Qua Di Thoi")
    private String fullName;

    @ApiModelProperty(example = "123 Ly Thuong Kiet")
    private String address;

    @ApiModelProperty(example = "Nam")
    private String gender;
}
