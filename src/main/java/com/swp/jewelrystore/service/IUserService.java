package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.RegisterDTO;
import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.model.response.UserResponseDTO;

import java.util.List;
import java.util.Map;

public interface IUserService {
    LoginResponseDTO login(UserDTO userDTO);
    List<UserResponseDTO> getAllUser(Map<String, String> params);
    void registerMember(RegisterDTO registerDTO);
    void softDeleteUser(List<Long> userId);
}
