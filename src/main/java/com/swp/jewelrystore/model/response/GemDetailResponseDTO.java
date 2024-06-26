package com.swp.jewelrystore.model.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GemDetailResponseDTO {
    private Long id;
    private String gemCode;
    private String gemName;
    private String origin;
    private String color;
    private String clarity;
    private Double caratWeight;
    private String cut;
    private String proportions;
    private String polish;
    private String symmetry;
    private String fluorescence;
}
