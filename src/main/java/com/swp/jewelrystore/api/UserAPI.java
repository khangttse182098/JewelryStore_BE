package com.swp.jewelrystore.api;

import com.swp.jewelrystore.constant.SystemConstant;
import com.swp.jewelrystore.model.dto.RegisterDTO;
import com.swp.jewelrystore.model.dto.UserDTO;
import com.swp.jewelrystore.model.response.LoginResponseDTO;
import com.swp.jewelrystore.model.response.ResponseDTO;
import com.swp.jewelrystore.model.response.UserResponseDTO;
import com.swp.jewelrystore.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( "/api/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserAPI {

    private final IUserService userService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserDTO userDTO) {
        LoginResponseDTO loginResponseDTO = userService.login(userDTO);
        return loginResponseDTO;
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "status", dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "phone", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "fullName", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "user_id", dataType = "Long", paramType = "query")
    })

    @GetMapping
    public List<UserResponseDTO> getAllUsers(@RequestParam Map<String, String> params) {
        List<UserResponseDTO> userResponseDTOS = userService.getAllUser(params);
        return userResponseDTOS;
    }

    @PostMapping
    public ResponseEntity<ResponseDTO> addOrUpdateUser(@RequestBody RegisterDTO registerDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            userService.addOrUpdateUser(registerDTO);
            responseDTO.setMessage(SystemConstant.ADD_USER_SUCCESSFULLY);
            responseDTO.setData(registerDTO);
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e){
            responseDTO.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/delete-{id}")
    public String deleteUser(@PathVariable List<Long> id) {
        userService.softDeleteUser(id);
        return SystemConstant.DELETE_USER;
    }

}
