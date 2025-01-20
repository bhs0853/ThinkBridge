package com.bhs.thinkbridge.controllers;

import com.bhs.thinkbridge.dtos.*;
import com.bhs.thinkbridge.requestDto.SignInRequestDTO;
import com.bhs.thinkbridge.requestDto.SignUpUserRequestDTO;
import com.bhs.thinkbridge.responseDto.AuthResponseDto;
import com.bhs.thinkbridge.services.JwtService;
import com.bhs.thinkbridge.services.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {

    private final UserAuthService userAuthService;
    private final JwtService jwtService;

    public UserAuthController(UserAuthService userAuthService, JwtService jwtService){
        this.userAuthService = userAuthService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpUserRequestDTO userDto){
        Optional<?> newUser = userAuthService.signUpUser(userDto);
        System.out.println("SIGN UP");

        if(newUser.isEmpty())
            return ResponseEntity.internalServerError().build();
        else if(newUser.get().getClass().equals(AuthResponseDto.class))
            return ResponseEntity.ok(newUser);
        else {
            Exception e = (Exception) newUser.get();
            return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody SignInRequestDTO requestDTO, HttpServletResponse servletResponse){
        Optional<?> authenticatedUser = userAuthService.authenticateUser(requestDTO);
        System.out.println("Sign In");

        if(authenticatedUser.isEmpty())
            return ResponseEntity.internalServerError().build();
        else if(authenticatedUser.get().getClass().equals(AuthResponseDto.class)) {
            AuthResponseDto responseDto = (AuthResponseDto) authenticatedUser.get();
            ResponseCookie cookie = ResponseCookie.from("JwtToken", responseDto.getToken())
                    .httpOnly(true)
                    .maxAge(jwtService.getExpirationTime())
                    .path("/")
                    .secure(false)
                    .build();
            servletResponse.setHeader("Set-Cookie", cookie.toString());
            return ResponseEntity.ok(authenticatedUser);
        }
        else{
            Exception e = (Exception) authenticatedUser.get();
            return ResponseEntity.badRequest().body(new ErrorDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

}
