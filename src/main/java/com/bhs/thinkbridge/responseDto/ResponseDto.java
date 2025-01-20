package com.bhs.thinkbridge.responseDto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private HttpStatus status;

    private String message;

}
