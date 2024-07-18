package com.swp.jewelrystore.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserSecurityDTO {
    private String userName;
    private String password;
    private Long status;
    private RoleDTO role;
}
