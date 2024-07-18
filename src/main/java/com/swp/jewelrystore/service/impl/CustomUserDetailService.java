package com.swp.jewelrystore.service.impl;


import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.dto.MyUserDetail;
import com.swp.jewelrystore.model.dto.RoleDTO;
import com.swp.jewelrystore.model.dto.UserSecurityDTO;
import com.swp.jewelrystore.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.isEmpty()){
            System.out.println("0 co");
        }else{
            System.out.println(username);
        }

        UserSecurityDTO userDTO = new UserSecurityDTO();
        UserEntity userEntity = userRepository.findOneByUserNameAndStatus(username, 1L);
        if(userEntity == null){
            throw new UsernameNotFoundException("Username not found");
        }
        userDTO.setPassword(userEntity.getPassword());
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setCode(userEntity.getRole().getCode());
        roleDTO.setName((userEntity.getRole().getName()));
        userDTO.setRole(roleDTO);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+userDTO.getRole().getCode()));
        MyUserDetail myUserDetail = new MyUserDetail(username,userDTO.getPassword(),true,true,true,true,authorities);
        BeanUtils.copyProperties(userDTO, myUserDetail);
        return myUserDetail;
    }
}
