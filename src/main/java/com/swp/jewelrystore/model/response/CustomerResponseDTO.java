package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponseDTO {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String gender;
    private int quantityOrder;
    private Double expense;
}
