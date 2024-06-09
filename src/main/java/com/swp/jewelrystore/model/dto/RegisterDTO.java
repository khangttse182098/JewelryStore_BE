package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    private Long role;
    private String fullName;
    private String userName;
    private String password;
    private String phone;
}
