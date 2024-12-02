package com.bhs.thinkbridge.dtos;

import com.bhs.thinkbridge.models.Tags;
import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {

    private String text;

    private String user_id;

    private List<Tags> tagsList;
}
