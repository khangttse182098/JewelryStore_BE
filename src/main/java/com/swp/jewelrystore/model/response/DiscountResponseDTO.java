package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiscountResponseDTO {
    private Long id;
    private String status;
    private String value;
    private String code;
    private String startDate;
    private String endDate;

}
