package com.bhs.thinkbridge.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Cascade({CascadeType.PERSIST,CascadeType.DELETE_ORPHAN})
    private String user_id;

    @Column(unique = true)
    private String user_name;

    @Column(unique = true)
    private String email;

    private String bio;
}
