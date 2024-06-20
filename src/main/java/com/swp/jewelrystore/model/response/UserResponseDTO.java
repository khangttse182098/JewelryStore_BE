package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String role;
    private String phone;
    private String status;
    private Double personalIncome;
    private int sellOrderQuantity;
}
