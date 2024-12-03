package com.bhs.thinkbridge.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseModel{

    @JsonIgnoreProperties("question_list")
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "question"),
            inverseJoinColumns = @JoinColumn(name = "tag")
    )
    private List<Tag> tag_list;

}
