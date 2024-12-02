package com.bhs.thinkbridge.models;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Answer extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "question",nullable = false)
    private Question question;
}
