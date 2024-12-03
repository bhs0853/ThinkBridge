package com.bhs.thinkbridge.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tag_id;

    @Column(nullable = false,unique = true)
    private String name;

    @JsonIgnoreProperties("tag_list")
    @ManyToMany(mappedBy = "tag_list")
    private List<Question> question_list;
}
