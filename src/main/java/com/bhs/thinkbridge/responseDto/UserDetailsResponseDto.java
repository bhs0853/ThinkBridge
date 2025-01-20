package com.bhs.thinkbridge.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsResponseDto extends ResponseDto {

    private String user_id;

    private String name;

    private String email;

    private Date created_at;

    private Date updated_at;

    private String bio;

    private Collection<? extends GrantedAuthority> authorities;

    @Builder
    public UserDetailsResponseDto(String user_id, String name, String email, Date created_at, Date updated_at, String bio, Collection<? extends GrantedAuthority> authorities, HttpStatus status, String message){
        super(status, message);
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.bio = bio;
        this.authorities = authorities;
    }

}
