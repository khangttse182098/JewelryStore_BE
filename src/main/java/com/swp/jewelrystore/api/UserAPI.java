package com.swp.jewelrystore.api;

import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.model.response.UserResponseDTO;
import com.swp.jewelrystore.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @GetMapping
    public List<UserResponseDTO> getAllUsers(@RequestParam Map<String, String> params) {
        List<UserResponseDTO> userResponseDTOS = userService.getAllUser(params);
        return userResponseDTOS;
    }
}
