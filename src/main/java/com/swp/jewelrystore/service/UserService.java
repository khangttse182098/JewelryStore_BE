package com.swp.jewelrystore.service;

import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LoginResponseDTO login(UserDTO userDTO) {
        LoginResponseDTO loginResponseDTO  = new LoginResponseDTO();
        UserEntity userEntity = userRepository.findByUsernameAndPasswordAndStatus(userDTO.getUsername(), userDTO.getPassword(), 1L);
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
}
