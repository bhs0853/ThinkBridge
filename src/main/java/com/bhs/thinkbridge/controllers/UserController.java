package com.bhs.thinkbridge.controllers;


import com.bhs.thinkbridge.dtos.UserDTO;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable("id") String id){
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent()){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<Optional<User>> createUser(@RequestBody UserDTO userDTO){
        Optional<User> newUser = userService.createUser(userDTO);
        if(newUser.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<User>> updateUser(@PathVariable("id") String id, @RequestBody UserDTO userDTO){
        Optional<User> user = userService.updateUser(id,userDTO);
        if(user.isPresent()){
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id){
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
