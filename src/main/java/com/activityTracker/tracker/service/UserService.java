package com.activityTracker.tracker.service;

import com.activityTracker.tracker.dto.requestDto.TaskDto;
import com.activityTracker.tracker.dto.requestDto.UserDto;
import com.activityTracker.tracker.entity.Task;
import com.activityTracker.tracker.entity.User;
import com.activityTracker.tracker.enums.ActivityStatus;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void createUser(UserDto userDto);

    User fetchUser(Long id);

    User loginUser(String email, String password);

    Task addTask(TaskDto taskDto, Long id);

    List<Task> getCompletedTask(Long id);

    List<Task> getPendingTask(Long id);

    List<Task> getInProgressTask(Long id);

    List<Task> getAllTaskByUser(Long id);

//    Optional<Task> getTaskByUserAndId(Long id, Long taskId);
//
//    Task findTaskByTitle(String title);

    Task updateTaskStatus(Long taskId, Long userId, ActivityStatus newStatus);

    void deleteTask(Long taskId, Long userId);

    Task editTask(Long userId, Long taskId, TaskDto taskDto);



/*
    void createUser(UserDto userDto);

    User fetchUser(Long id);

    User loginUser(String email, String password);

    Task addTask(TaskDto taskDto, Long id);

    List<Task> getCompletedTask(Long id);

    List<Task> getPendingTask(Long id);

    List<Task> getInProgressTask(Long id);

    List<Task> getAllTaskByUser(Long id);

    Optional<Task> getTaskByUserAndId(Long id, Long taskId);

    Task findTaskByTitle(String title);

    Task pendingToInProgress(Long id, Long taskId);

    Task inProgressToCompleted(Long id, Long taskId);

    Task pendingToCompleted(Long id, Long taskId);

    Task inProgressToPending(Long id, Long taskId);

    Task completedToInProgress(Long id, Long taskId);

    void deleteTask(Long id, Long taskId);

    Task editTask(TaskDto taskDto, Long id, Long taskId);*/
}
