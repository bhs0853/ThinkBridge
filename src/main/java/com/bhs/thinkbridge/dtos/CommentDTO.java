package com.bhs.thinkbridge.dtos;


import lombok.Data;

@Data
public class CommentDTO {

    private String text;

    private String user_id;

    private String answer_id;
}
