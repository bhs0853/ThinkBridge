package com.bhs.thinkbridge.adapter;

import com.bhs.thinkbridge.models.User;
import com.bhs.thinkbridge.responseDto.UserDetailsResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter {

    public UserDetailsResponseDto toResponseDto(User user, HttpStatus status, String message){
        return UserDetailsResponseDto
                .builder()
                .user_id(user.getUser_id())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .created_at(user.getCreated_at())
                .updated_at(user.getUpdated_at())
                .authorities(user.getAuthorities())
                .status(status)
                .message(message)
                .build();

    }
}
