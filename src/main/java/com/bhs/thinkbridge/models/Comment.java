package com.bhs.thinkbridge.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;

import java.util.Optional;

public class Comment extends BaseModel{

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "answer_id"),@JoinColumn(name = "comment_id")})
    private Optional<?> parent;
}
