package com.bhs.thinkbridge.models;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "question",nullable = false)
    private Question question;

}
