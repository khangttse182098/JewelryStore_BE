package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
@Getter
@Setter
public class CounterResponseDTO {
    private Long id;

    private String counterNo;

    private String description;
}
