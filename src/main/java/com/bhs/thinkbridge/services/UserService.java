package com.bhs.thinkbridge.services;

import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.requestDto.UpdatePasswordRequestDto;
import com.bhs.thinkbridge.requestDto.UpdateUserRequestDTO;

import java.util.Optional;

public interface UserService {

    Optional<User> getUser();

    Optional<User> updateUser(UpdateUserRequestDTO userDTO);

    void updatePassword(UpdatePasswordRequestDto requestDTO);

    void deleteUser();
    
}
