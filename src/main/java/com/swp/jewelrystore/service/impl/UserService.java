package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.converter.UserConverter;
import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.dto.RegisterDTO;
import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.model.response.UserResponseDTO;
import com.swp.jewelrystore.repository.RoleRepository;
import com.swp.jewelrystore.repository.UserRepository;
import com.swp.jewelrystore.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserConverter userConverter;


    @Override
    public LoginResponseDTO login(UserDTO userDTO) {
        LoginResponseDTO loginResponseDTO  = new LoginResponseDTO();
        UserEntity userEntity = userRepository.findByUserName(userDTO.getUsername());
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        if (!bCrypt.matches(userDTO.getPassword(), userEntity.getPassword())) {
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

    @Override
    public void registerMember(RegisterDTO registerDTO) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        UserEntity userEntity = modelMapper.map(registerDTO, UserEntity.class);
        userEntity.setRole(roleRepository.findById(registerDTO.getRole()).get());
        userEntity.setPassword(bCrypt.encode(registerDTO.getPassword()));
        userEntity.setStatus(1L);
        userRepository.save(userEntity);
    }


}
