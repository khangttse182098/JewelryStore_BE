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

    @NotEmpty(message="Vai trò phải được cung cấp")
    private String role;

    @NotEmpty(message="Tên đầy đủ của người dùng không được để trống")
    private String fullName;

    @NotEmpty(message="Tên đăng nhập không thể trống")
    private String userName;

    @NotEmpty(message="Mật khẩu không thể trống")
    private String password;

    @NotEmpty(message="Số điện thoại không được trống")
    private String phone;
}
