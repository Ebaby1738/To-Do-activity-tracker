package com.activityTracker.tracker.controller;


import com.activityTracker.tracker.dto.requestDto.TaskDto;
import com.activityTracker.tracker.dto.requestDto.UserDto;
import com.activityTracker.tracker.dto.requestDto.UserLoginDto;
import com.activityTracker.tracker.entity.Task;
import com.activityTracker.tracker.entity.User;
import com.activityTracker.tracker.enums.ActivityStatus;
import com.activityTracker.tracker.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tracker")
public class UserController {

    private final UserService userService;
    private final HttpSession httpSession;

    public UserController(UserService userService, HttpSession httpSession) {
        this.userService = userService;
        this.httpSession = httpSession;
    }

    @PostMapping("/signup")
    public String createUser(@Valid @RequestBody UserDto userDto){
        userService.createUser(userDto);
        return "User signup successfully";
    }

    @PostMapping("/login")
    public User login(@RequestBody UserLoginDto userLoginDto){
        User user = userService.loginUser(userLoginDto.getEmail(), userLoginDto.getPassword());
        httpSession.setAttribute("userId", user.getId());

        return user;
    }
    @PostMapping("/createTask")
    public String createTask(@RequestBody TaskDto taskDto){
        userService.addTask(taskDto, (Long)httpSession.getAttribute("userId"));
        return "task created successfully";

    }

    @GetMapping("/user/{id}/user")
    public User getUser(@PathVariable("id") Long userId) {

        return userService.fetchUser(userId);
    }

    @GetMapping("/task/{id}/tasks")
    public List<Task> getTask() {
        return userService.getAllTaskByUser((Long)httpSession.getAttribute("userId"));
    }


    @PostMapping("/updateTaskStatus/{taskId}")

    public Task updateTaskStatus(@PathVariable String taskId, @RequestParam("status") String status) {

        return userService.updateTaskStatus(Long.parseLong(taskId), (Long) httpSession.getAttribute("userId"), ActivityStatus.valueOf(status));

    }


    @PostMapping("/deleteTask/{id}")

    public String deleteTask(@PathVariable String id) {

        userService.deleteTask(Long.parseLong(id), (Long) httpSession.getAttribute("userId"));

        return "Task Deleted Successfully";

    }


    @PostMapping("/editTask/{id}")

    public Task editTask(@PathVariable String id, @RequestBody TaskDto taskDto) {

        return userService.editTask((Long) httpSession.getAttribute("userId"), Long.parseLong(id), taskDto);

    }






}
