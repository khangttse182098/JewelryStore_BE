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
        UserEntity user = userRepository.findOneByUserNameAndStatus(username, 1L);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().getCode()));
        return new MyUserDetail(user.getUserName(), user.getPassword(),true,true,true,true, authorities);
    }
}
