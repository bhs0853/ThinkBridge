package com.bhs.thinkbridge.services;

import com.bhs.thinkbridge.dtos.ErrorDTO;
import com.bhs.thinkbridge.dtos.UserDTO;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id){
        return userRepository.findById(id);
    }

    public Optional<?> createUser(UserDTO userDTO){
        if(userDTO.getUser_name() == null || userDTO.getEmail() == null){
            ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST);
            error.setError("Both Username and Email are Required");
            return Optional.of(error);
        }
        if(userRepository.existsByEmailAndUserName(userDTO.getEmail(),userDTO.getUser_name())){
            ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST);
            error.setError("Username or Email Already Exists!!! Please try with new one");
            return Optional.of(error);
        }
        User newUser = User.builder()
                .user_name(userDTO.getUser_name())
                .email(userDTO.getEmail())
                .bio(userDTO.getBio())
                .build();
        userRepository.save(newUser);
        return Optional.of(newUser);
    }

    public Optional<User> updateUser(String id,UserDTO userDTO){
        if(userRepository.existsById(id)){
            userRepository.updateUser(id,userDTO.getUser_name(),userDTO.getEmail(),userDTO.getBio());
        }
        return userRepository.findById(id);
    }

    public void deleteUserById(String id){
        userRepository.deleteById(id);
    }
}
