package com.swp.jewelrystore.api;

import com.swp.jewelrystore.entity.UserEntity;
import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.repository.UserRepository;
import com.swp.jewelrystore.service.IUserService;
import com.swp.jewelrystore.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class HomeAPI {
    private final IUserService userService;
    private final UserRepository userRepository;

    @GetMapping(value = "/")
    public LoginResponseDTO login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());
        String userName =  SecurityUtils.getPrincipal().getUsername();
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userName);
        LoginResponseDTO loginResponseDTO = userService.login(userDTO);
        return loginResponseDTO;
    }
}
