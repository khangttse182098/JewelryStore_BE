package com.swp.jewelrystore.service;

import com.swp.jewelrystore.model.dto.RegisterDTO;
import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.model.response.UserResponseDTO;
import com.swp.jewelrystore.model.response.UserRevenueResponseDTO;

import java.util.List;
import java.util.Map;

public interface IUserService {
    LoginResponseDTO login(UserDTO userDTO);
    List<UserResponseDTO> getAllUser(Map<String, String> params);
    void addOrUpdateUser(RegisterDTO registerDTO);
    void softDeleteUser(List<Long> userId);
    List<UserRevenueResponseDTO> getUserRevenue(Map<String, String> params);
}
