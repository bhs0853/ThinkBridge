package com.bhs.thinkbridge.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tags {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String tag_id;

    private String name;

    @ManyToMany(mappedBy = "tagsList")
    private List<Question> questionList;
}
