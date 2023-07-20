package com.activityTracker.tracker.dto.requestDto;

import com.activityTracker.tracker.entity.User;
import com.activityTracker.tracker.enums.ActivityStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class TaskDto {
    private String title;
    private ActivityStatus activityStatus;

    private String description;
}
