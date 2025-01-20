package com.bhs.thinkbridge.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto extends ResponseDto {

    private String email;

    private String token;

    @Builder
    public AuthResponseDto(String email, String token, HttpStatus status, String message){
        super(status, message);
        this.email = email;
        this.token = token;
    }

}
