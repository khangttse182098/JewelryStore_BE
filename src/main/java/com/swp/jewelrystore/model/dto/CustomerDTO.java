package com.swp.jewelrystore.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String phoneNumber;
    private String fullName;
    private String address;
    private String gender;
}
