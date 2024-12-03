package com.bhs.thinkbridge.dtos;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {

    private String text;

    private String user_id;

    private List<String> tagsList;
}
