package com.activityTracker.tracker.dto.requestDto;

import com.activityTracker.tracker.entity.Task;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Data
public class UserDto {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private List<Task> task = new ArrayList<>();
}
