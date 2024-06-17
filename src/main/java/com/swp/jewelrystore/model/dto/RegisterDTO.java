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

    @NotNull(message="Role id is required")
    private Long role;

    @NotEmpty(message="Full name of user is required")
    private String fullName;

    @NotEmpty(message="Username can not empty")
    private String userName;

    @NotEmpty(message="Password can not empty")
    private String password;

    @NotEmpty(message="Phone number can not empty")
    private String phone;
}
