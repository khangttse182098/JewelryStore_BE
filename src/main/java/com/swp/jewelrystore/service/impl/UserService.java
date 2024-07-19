package com.swp.jewelrystore.service.impl;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.converter.UserConverter;
import com.swp.jewelrystore.converter.UserRoleConverter;
import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.dto.RegisterDTO;
import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.model.response.UserResponseDTO;
import com.swp.jewelrystore.model.response.UserRevenueResponseDTO;
import com.swp.jewelrystore.repository.RoleRepository;
import com.swp.jewelrystore.repository.UserRepository;
import com.swp.jewelrystore.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final UserConverter userConverter;


    @Override
    public LoginResponseDTO login(UserDTO userDTO) {
        LoginResponseDTO loginResponseDTO  = new LoginResponseDTO();
        UserEntity userEntity = userRepository.findByUserName(userDTO.getUsername());
        if(userEntity != null){
            loginResponseDTO = modelMapper.map(userEntity, LoginResponseDTO.class);
            loginResponseDTO.setRoleCode(userEntity.getRole().getCode());
            loginResponseDTO.setRoleName(userEntity.getRole().getName());
            loginResponseDTO.setMessage(SystemConstant.LOGIN_SUCCESS);
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
    public void addOrUpdateUser(RegisterDTO registerDTO) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        // registerDTO do not have id
        if ( registerDTO.getId() != null){
            UserEntity userEntity = modelMapper.map(registerDTO, UserEntity.class);
            // existed user
            // when update, front-end will not give the password to backend -> get existed password
            UserEntity existedUser = userRepository.findById(registerDTO.getId()).get();
            userEntity.setUserName(existedUser.getUserName());
            userEntity.setPassword(existedUser.getPassword());
            userEntity.setRole(roleRepository.findById(UserRoleConverter.convertRoleFromTextToNumber(registerDTO.getRole())).get());
            userEntity.setStatus(1L);
            userRepository.save(userEntity);
        } else {
            if (userRepository.findByPhone(registerDTO.getPhone()) != null){
                throw new DataIntegrityViolationException("Phone number is already existed");
            }
            UserEntity userEntity = modelMapper.map(registerDTO, UserEntity.class);
            userEntity.setRole(roleRepository.findById(UserRoleConverter.convertRoleFromTextToNumber(registerDTO.getRole())).get());
            userEntity.setPassword(bCrypt.encode(registerDTO.getPassword()));
            userEntity.setStatus(1L);
            userRepository.save(userEntity);
        }
    }

    @Override
    public void softDeleteUser(List<Long> userId) {
         List<UserEntity> listUser = userRepository.findAllByIdIn(userId);
         for (UserEntity item: listUser){
             item.setStatus(0L);
         }
    }

    @Override
    public List<UserRevenueResponseDTO> getUserRevenue(Map<String, String> params) {
        return userRepository.getUserRevenue(params);
    }

}
