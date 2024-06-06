package com.swp.jewelrystore.model.response;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CriteriaResponseDTO {
    List<GoldCriteriaResponseDTO> goldCriteriaResponseDTO;
    List<DiamondCriteriaResponseDTO> diamondCriteriaResponseDTO;
}
