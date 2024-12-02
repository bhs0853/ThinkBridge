package com.bhs.thinkbridge.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Question extends BaseModel{

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "question"),
            inverseJoinColumns = @JoinColumn(name = "tag")
    )
    private List<Tags> tagsList;
}
