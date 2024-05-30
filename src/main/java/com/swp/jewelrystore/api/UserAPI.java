package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/user")
@CrossOrigin
public class UserAPI {
    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserDTO userDTO) {
        LoginResponseDTO loginResponseDTO = userService.login(userDTO);
        return loginResponseDTO;
    }
}
