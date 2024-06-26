package com.swp.jewelrystore.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRevenueResponseDTO {
    private Long sellerId;
    private String fullname;
    private Double revenue;
}
