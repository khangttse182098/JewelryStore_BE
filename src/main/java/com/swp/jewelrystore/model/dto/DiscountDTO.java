package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDTO {
    private String value;
    private String code;
    private String startDateDTO;
    private String endDateDTO;
}
