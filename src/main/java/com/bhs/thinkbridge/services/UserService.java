package com.bhs.thinkbridge.services;

import com.bhs.thinkbridge.dtos.UpdateUserRequestDTO;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return userRepository.findUserByEmail(email);
    }

    public Optional<User> updateUser(UpdateUserRequestDTO userDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        User user = userRepository.findUserByEmail(email).get();
        userRepository.updateUser(user.getUser_id(), userDTO.getEmail(), userDTO.getBio(), passwordEncoder.encode(userDTO.getPassword()), new Date());
        return userRepository.findUserByEmail(email);
    }

    public void deleteUser(){
        Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        userRepository.deleteUserByEmail(email);
    }
    
}
