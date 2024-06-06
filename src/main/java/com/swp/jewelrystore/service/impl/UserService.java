package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.UserConverter;
import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.model.response.UserResponseDTO;
import com.swp.jewelrystore.repository.UserRepository;
import com.swp.jewelrystore.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserConverter userConverter;

    @Override
    public LoginResponseDTO login(UserDTO userDTO) {
        LoginResponseDTO loginResponseDTO  = new LoginResponseDTO();
        UserEntity userEntity = userRepository.findByUserNameAndPasswordAndStatus(userDTO.getUsername(), userDTO.getPassword(), 1L);
        if (userEntity == null) {
            loginResponseDTO.setMessage("Invalid username or password!");
        }else {
            loginResponseDTO = modelMapper.map(userEntity, LoginResponseDTO.class);
            loginResponseDTO.setRoleCode(userEntity.getRole().getCode());
            loginResponseDTO.setRoleName(userEntity.getRole().getName());
            loginResponseDTO.setMessage("Successfully logged in!");
        }
        return loginResponseDTO;
    }

    @Override
    public List<UserResponseDTO> getAllUser(Map<String, String> params) {
        List<UserEntity> userEntities = userRepository.getAllUsers(params);
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        for(UserEntity userEntity : userEntities) {
            userResponseDTOS.add(userConverter.toUserResponseDTO(userEntity));
        }
        return userResponseDTOS;
    }
}
