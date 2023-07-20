package com.activityTracker.tracker.dao;

import com.activityTracker.tracker.entity.Task;
import com.activityTracker.tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);


}
