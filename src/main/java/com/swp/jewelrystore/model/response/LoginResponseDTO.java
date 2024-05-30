package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
        private Long userId;
        private String fullName;
        private String roleCode;
        private String roleName;
        private String phone;
        private String userName;
        private String message;
}
