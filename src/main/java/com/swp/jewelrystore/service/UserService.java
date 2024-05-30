package com.swp.jewelrystore.service;

import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

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
}
