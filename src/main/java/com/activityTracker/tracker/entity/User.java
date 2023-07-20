package com.activityTracker.tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userT")
public class User {
    @Id
    @SequenceGenerator(name = "user_id", sequenceName = "user_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
    private Long id;
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    private String password;

    @OneToMany(orphanRemoval = true, mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private List<Task> task = new ArrayList<>();
}
