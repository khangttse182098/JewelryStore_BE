package com.swp.jewelrystore.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    @ApiModelProperty(example = "seller")
    private String username;

    @ApiModelProperty(example = "123456")
    private String password;
}
