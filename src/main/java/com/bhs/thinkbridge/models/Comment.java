package com.bhs.thinkbridge.models;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends BaseModel{

    @ManyToOne
    @JoinColumn(name = "answer", nullable = false)
    private Answer answer;

}
