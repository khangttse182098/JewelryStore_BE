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

    @NotEmpty(message="Nguồn gốc không được để trống")
    @JsonProperty("origin")
    @ApiModelProperty(example="Nhân tạo")
    private String origin;

    @NotEmpty(message="Màu sắc không được để trống")
    @JsonProperty("color")
    @ApiModelProperty(example="H")
    private String color;

    @NotEmpty(message="Độ tinh khiết không được để trống")
    @JsonProperty("clarity")
    @ApiModelProperty(example="VVS1")
    private String clarity;

    @NotNull(message="Khoảng đầu của trọng lượng carat không được để trống")
    @JsonProperty("caratWeightFrom")
    @ApiModelProperty(example="2.3")
    @Min(value = 0, message="Giá trị trọng lượng phải lớn hơn 0")
    private Double caratWeightFrom;

    @NotNull(message="Khoảng cuối của trọng lượng carat không được để trống")
    @JsonProperty("caratWeightTo")
    @ApiModelProperty(example="2.65")
    @Min(value = 0, message="Giá trị trọng lượng phải lớn hơn 0")
    private Double caratWeightTo;

    @NotEmpty(message="Giác cắt không được để trống")
    @JsonProperty("cut")
    @ApiModelProperty(example="PR")
    private String cut;

    @NotNull(message="Giá mua không thể để trống")
    @JsonProperty("buyPrice")
    @ApiModelProperty(example="25000000")
    @Min(value = 0, message="Giá mua phải lớn hơn 0")
    private Double buyPrice;

    @NotNull(message="Giá bán không thể để trống")
    @JsonProperty("sellPrice")
    @ApiModelProperty(example="27250000")
    @Min(value = 0, message="Giá bán phải lớn hơn 0")
    private Double sellPrice;

    @NotEmpty(message="Ngày hiệu lực không được để trống")
    @JsonProperty("effectDate")
    @ApiModelProperty(example="20/06/2024 12:00")
    private String effecttDate;
}
