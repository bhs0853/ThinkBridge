package com.bhs.thinkbridge.controllers;


import com.bhs.thinkbridge.adapter.UserAdapter;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.requestDto.UpdatePasswordRequestDto;
import com.bhs.thinkbridge.requestDto.UpdateUserRequestDTO;
import com.bhs.thinkbridge.responseDto.ResponseDto;
import com.bhs.thinkbridge.responseDto.UserDetailsResponseDto;
import com.bhs.thinkbridge.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserAdapter userAdapter;

    public UserController(UserService userService, UserAdapter userAdapter){
        this.userService = userService;
        this.userAdapter = userAdapter;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailsResponseDto> getAuthenticatedUser(){
        Optional<User> responseDto = userService.getUser();
        return responseDto.map(user -> ResponseEntity.ok(userAdapter.toResponseDto(user, HttpStatus.OK, "Fetched User Successfully")))
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDetailsResponseDto> updateUser(@RequestBody UpdateUserRequestDTO userDTO) {
        Optional<User> responseDto = userService.updateUser(userDTO);
        return responseDto.map(user -> ResponseEntity.ok(userAdapter.toResponseDto(user, HttpStatus.OK, "Updated User Successfully")))
                .orElseGet(() -> ResponseEntity.internalServerError().build());
    }

    @PatchMapping("/updatePassword")
    public ResponseEntity<ResponseDto> updatePassword(@RequestBody UpdatePasswordRequestDto requestDTO){
        userService.updatePassword(requestDTO);
        return ResponseEntity.ok(new ResponseDto(HttpStatus.OK,"Updated Password Successfully"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        userService.deleteUser();
        System.out.println("delete user");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
