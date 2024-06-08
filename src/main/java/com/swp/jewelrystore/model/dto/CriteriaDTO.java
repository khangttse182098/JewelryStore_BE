package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CriteriaDTO {

    @ApiModelProperty(example = "[{\"id\": 1, \"name\": \"Vàng miếng SJC 999.9\", \"weight\": 2}, {\"id\": 2, \"name\": \"Vàng Phúc Lộc Tài 999.9\", \"weight\": 1}]")
    private List<GoldCriteriaDTO> listGoldCriteria;

    @ApiModelProperty(example = "[{\"origin\": \"Tự nhiên\", \"color\": \"D\", \"clarity\": \"VVS2\", \"caratWeight\": 1, \"cut\": \"GD\"}, {\"origin\": \"Nhân tạo\", \"color\": \"L\", \"clarity\": \"FL\", \"caratWeight\": 1, \"cut\": \"VG\"}]")
    private List<DiamondCriteriaDTO> listDiamondCriteria;
}
