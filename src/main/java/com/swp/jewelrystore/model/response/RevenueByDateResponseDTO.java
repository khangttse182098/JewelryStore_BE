package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevenueByDateResponseDTO {
    private Double totalPrice;
    private String createdDate;
    private Long numberOfOrder;

}
