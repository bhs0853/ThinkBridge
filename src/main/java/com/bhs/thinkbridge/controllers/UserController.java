package com.bhs.thinkbridge.controllers;


import com.bhs.thinkbridge.dtos.UpdateUserRequestDTO;
import com.bhs.thinkbridge.dtos.UserDetailsResponseDto;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailsResponseDto> getAuthenticatedUser(){
        Optional<User> user = userService.getUser();
        System.out.println("user");
        if(user.isPresent()){
            User authenticatedUser = (User) user.get();
            UserDetailsResponseDto responseDto = UserDetailsResponseDto
                                                    .builder()
                                                    .user_id(authenticatedUser.getUser_id())
                                                    .email(authenticatedUser.getEmail())
                                                    .bio(authenticatedUser.getBio())
                                                    .created_at(authenticatedUser.getCreated_at())
                                                    .updated_at(authenticatedUser.getUpdated_at())
                                                    .authorities(authenticatedUser.getAuthorities())
                                                    .status(HttpStatus.OK)
                                                    .build();
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.internalServerError().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDetailsResponseDto> updateUser(@RequestBody UpdateUserRequestDTO userDTO){
        Optional<User> user = userService.updateUser(userDTO);
        System.out.println("update User");

        if(user.isPresent()){
            User authenticatedUser = (User) user.get();
            UserDetailsResponseDto responseDto = UserDetailsResponseDto
                    .builder()
                    .user_id(authenticatedUser.getUser_id())
                    .email(authenticatedUser.getEmail())
                    .bio(authenticatedUser.getBio())
                    .created_at(authenticatedUser.getCreated_at())
                    .updated_at(authenticatedUser.getUpdated_at())
                    .authorities(authenticatedUser.getAuthorities())
                    .status(HttpStatus.OK)
                    .build();
            return ResponseEntity.ok(responseDto);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        userService.deleteUser();
        System.out.println("delete user");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
