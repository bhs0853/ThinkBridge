package com.bhs.thinkbridge.services;

import com.bhs.thinkbridge.requestDto.UpdatePasswordRequestDto;
import com.bhs.thinkbridge.requestDto.UpdateUserRequestDTO;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> updateUser(UpdateUserRequestDTO userDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        User user = userRepository.findUserByEmail(email).get();
        userRepository.updateUser(user.getUser_id(), userDTO.getEmail(), userDTO.getBio(), new Date());
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void updatePassword(UpdatePasswordRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        User user = userRepository.findUserByEmail(email).get();
        userRepository.updatePassword(user.getUser_id(), passwordEncoder.encode(requestDto.getPassword()));
    }

    @Override
    public void deleteUser(){
        Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getPrincipal().toString();
        userRepository.deleteUserByEmail(email);
    }
    
}
