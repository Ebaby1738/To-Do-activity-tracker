package com.activityTracker.tracker.entity;


import com.activityTracker.tracker.enums.ActivityStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "taskT")
public class Task {
    @Id
//    @SequenceGenerator(name = "task_id", sequenceName = "task_id", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp completedAt;


    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", referencedColumnName = "id")

    private User user;

}
