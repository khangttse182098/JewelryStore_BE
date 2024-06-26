package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Valid
public class RegisterDTO {
    private Long id;
    private String role;
    private String fullName;
    private String userName;
    private String password;
    private String phone;
}
