package com.swp.jewelrystore.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Valid
public class DiamondDTO {

    @JsonProperty("id")
    @ApiModelProperty(example="")
    private Long id;

    @NotEmpty(message="Tên kim cương không được để trống")
    @JsonProperty("gemName")
    @ApiModelProperty(example="")
    private String gemName;

    @JsonProperty("origin")
    @ApiModelProperty(example="Nhân tạo")
    private String origin;


    @JsonProperty("color")
    @ApiModelProperty(example="Z")
    private String color;

    @JsonProperty("clarity")
    @ApiModelProperty(example="I1")
    private String clarity;

    @NotNull(message="Trọng lượng carat không được để trống")
    @JsonProperty("caratWeight")
    @ApiModelProperty(example="2.2")
    @Min(value = 0, message="Giá trị trọng lượng phải lớn hơn 0")
    private Double caratWeight;

    @JsonProperty("cut")
    @ApiModelProperty(example="GD")
    private String cut;

    @JsonProperty("proportions")
    @ApiModelProperty(example="Đai mỏng")
    private String proportions;

    @JsonProperty("polish")
    @ApiModelProperty(example="Khá")
    private String polish;

    @JsonProperty("symmetry")
    @ApiModelProperty(example="Tốt")
    private String symmetry;

    @JsonProperty("fluorescence")
    @ApiModelProperty(example="Mạnh")
    private String fluorescence;


}
