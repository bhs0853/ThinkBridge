package com.bhs.thinkbridge.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDto {

    private String user_id;

    private String email;

    private Date created_at;

    private Date updated_at;

    private String bio;

    private Collection<? extends GrantedAuthority> authorities;

    private HttpStatus status;

}
