package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDTO {
    @ApiModelProperty(example = "xem model",  value = "sửa id thành \"\" nếu muốn thêm discount, sửa id thành \"3\" nếu muốn update discount có id = 3")
    private Long id;
    @ApiModelProperty(example = "30")
    private Long value;
    @ApiModelProperty(example = "DISCOUNT30")
    private String code;
    @ApiModelProperty(example = "20:10:00 07/06/2024")
    private String startDateDTO;
    @ApiModelProperty(example = "20:10:00 07/12/2024")
    private String endDateDTO;
}
