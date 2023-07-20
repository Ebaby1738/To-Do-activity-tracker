package com.activityTracker.tracker.dao;

import com.activityTracker.tracker.entity.Task;
import com.activityTracker.tracker.entity.User;
import com.activityTracker.tracker.enums.ActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskDao extends JpaRepository<Task, Long> {
    List<Task> findTaskByUser(User user);
    Optional<Task> findTasksByUserAndId(User user, Long id);

    List<Task> findTasksByUserAndActivityStatus(User user, ActivityStatus taskStatus);

//    List<Task> findTasksByUserAndId(User user, Long taskId);

}
