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
    private Long status;
    private Double personalIncome;
}
