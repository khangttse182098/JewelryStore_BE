package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;

public interface IUserService {
    LoginResponseDTO login(UserDTO userDTO);
}
