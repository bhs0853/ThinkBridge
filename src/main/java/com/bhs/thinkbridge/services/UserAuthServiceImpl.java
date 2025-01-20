package com.bhs.thinkbridge.services;

import com.bhs.thinkbridge.responseDto.AuthResponseDto;
import com.bhs.thinkbridge.requestDto.SignInRequestDTO;
import com.bhs.thinkbridge.requestDto.SignUpUserRequestDTO;
import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthServiceImpl implements UserAuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public UserAuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Optional<?> signUpUser(SignUpUserRequestDTO userDTO){
        try{
            User newUser = User
                            .builder()
                            .name(userDTO.getName())
                            .email(userDTO.getEmail())
                            .bio(userDTO.getBio())
                            .password(passwordEncoder.encode(userDTO.getPassword()))
                            .build();
            userRepository.save(newUser);
            return Optional.of(AuthResponseDto
                            .builder()
                            .status(HttpStatus.CREATED)
                            .message("Sign Up Successful")
                            .email(userDTO.getEmail())
                            .build());
        } catch (Exception e) {
            return Optional.of(e);
        }
    }

    @Override
    public Optional<?> authenticateUser(SignInRequestDTO signInRequestDTO){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInRequestDTO.getEmail(),
                            signInRequestDTO.getPassword()
                    )
            );
            UserDetails user = userDetailsService.loadUserByUsername(signInRequestDTO.getEmail());
            String token = jwtService.generateToken(user);
            return Optional.of(
                    AuthResponseDto
                            .builder()
                            .email(signInRequestDTO.getEmail())
                            .token(token)
                            .status(HttpStatus.OK)
                            .message("SignIn Successful")
                            .build()
            );
        } catch (Exception e) {
            return Optional.of(e);
        }
    }

}
