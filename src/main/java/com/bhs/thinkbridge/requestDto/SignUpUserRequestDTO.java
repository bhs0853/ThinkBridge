package com.bhs.thinkbridge.requestDto;

import lombok.Data;

@Data
public class SignUpUserRequestDTO {

    private String name;

    private String email;

    private String password;

    private String bio;
}
