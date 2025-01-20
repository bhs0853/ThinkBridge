package com.bhs.thinkbridge.services;

import com.bhs.thinkbridge.requestDto.SignInRequestDTO;
import com.bhs.thinkbridge.requestDto.SignUpUserRequestDTO;

import java.util.Optional;

public interface UserAuthService {

    Optional<?> signUpUser(SignUpUserRequestDTO userDTO);

    Optional<?> authenticateUser(SignInRequestDTO signInRequestDTO);

}
