package com.bhs.thinkbridge.dtos;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorDTO {

    private int status;
    private String error;

    public ErrorDTO(HttpStatus status){
        this.status = status.value();
        this.error = status.getReasonPhrase();
    }
    public ErrorDTO(HttpStatus status,String error){
        this.status = status.value();
        this.error = error;
    }

}
