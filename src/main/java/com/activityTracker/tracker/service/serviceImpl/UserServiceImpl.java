package com.activityTracker.tracker.service.serviceImpl;

import com.activityTracker.tracker.dao.TaskDao;
import com.activityTracker.tracker.dao.UserDao;
import com.activityTracker.tracker.dto.requestDto.TaskDto;
import com.activityTracker.tracker.dto.requestDto.UserDto;
import com.activityTracker.tracker.entity.Task;
import com.activityTracker.tracker.entity.User;
import com.activityTracker.tracker.enums.ActivityStatus;
import com.activityTracker.tracker.exceptions.CustomAppException;
import com.activityTracker.tracker.exceptions.ResourceAlreadyExistException;
import com.activityTracker.tracker.exceptions.ResourceNotFoundException;
import com.activityTracker.tracker.service.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final TaskDao taskDao;

    public UserServiceImpl(UserDao userDao, TaskDao taskDao) {
        this.userDao = userDao;
        this.taskDao = taskDao;
    }

    @Override
    public void createUser(UserDto userDto){
       Optional<User> checkIfUserInDb = userDao.findByEmail(userDto.getEmail());
       if (checkIfUserInDb.isPresent()){
           throw new ResourceAlreadyExistException("User already exist");
       }
       User newUser = new User();
       newUser.setName(userDto.getName());
       newUser.setEmail(userDto.getEmail());
       newUser.setPassword(userDto.getPassword());

        userDao.save(newUser);

    }

    @Override
    public User fetchUser(Long id){
        Optional <User>  checkForUser = userDao.findById(id);
        if (checkForUser.isPresent()){
            return checkForUser.get();
        }
        throw new ResourceNotFoundException("The user does not exist");
    }

    @Override
    public User loginUser(String email, String password){
        User checkIfUserExist = userDao.findByEmailAndPassword(email, password)
                .orElseThrow(()->new ResourceNotFoundException("Invalid email or password"));
        return checkIfUserExist;
    }


    @Override
    public Task addTask(TaskDto taskDto, Long id){
        User checkIfUserExist = userDao.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("user doesn't exist"));
        Task newTask = new Task();
        newTask.setTitle(taskDto.getTitle());
        newTask.setDescription(taskDto.getDescription());
        newTask.setUser(checkIfUserExist);
        newTask.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        newTask.setActivityStatus(ActivityStatus.PENDING);

        return taskDao.save(newTask);
    }

    @Override
    public List<Task> getCompletedTask(Long id){
        User checkIfUserExist = userDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("User doesn't exist"));
        return taskDao.findTasksByUserAndActivityStatus(checkIfUserExist, ActivityStatus.COMPLETED);

    }

    @Override
    public List<Task> getPendingTask(Long id){
        User checkIfUserExist = userDao.findById(id).orElseThrow(()-> new ResourceNotFoundException("User doesn't exist"));
        return taskDao.findTasksByUserAndActivityStatus(checkIfUserExist, ActivityStatus.PENDING);

    }

    @Override
    public List<Task> getInProgressTask(Long id) {
        User checkIfUserExist = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
        return taskDao.findTasksByUserAndActivityStatus(checkIfUserExist, ActivityStatus.IN_PROGRESS);

    }

    @Override
    public List<Task> getAllTaskByUser(Long id){
        User checkIfUserExist = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
        List<Task> findAllTask = taskDao.findTaskByUser(checkIfUserExist);
        if(findAllTask.isEmpty()){
            throw new CustomAppException("No task exist");
        }
        return findAllTask;
    }

//    @Override
//    public Optional<Task> getTaskByUserAndId(Long id, Long taskId){
//        User checkIfUserExist = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
//        Optional<Task> checkTaskById = taskDao.findTaskByUserAndId(checkIfUserExist, taskId);
//        if (checkTaskById.isPresent()){
//            return checkTaskById;
//        }
//        throw new CustomAppException("No task exists");
//    }
//
//    @Override
//    public Task findTaskByTitle(String title){
//        Optional<Task> checkTaskByTitle = taskDao.findTasksByTitle(title);
//        if (checkTaskByTitle.isPresent()){
//            return checkTaskByTitle.get();
//        }
//        throw new CustomAppException("Task  does not exist");
//    }

    @Override
    public Task updateTaskStatus(Long taskId, Long userId, ActivityStatus newStatus) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));
        Task task = taskDao.findTasksByUserAndId(user, taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (newStatus == ActivityStatus.IN_PROGRESS) {
            if (task.getActivityStatus() == ActivityStatus.PENDING) {
                task.setActivityStatus(ActivityStatus.IN_PROGRESS);
                task.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                return taskDao.save(task);
            } else {
                throw new CustomAppException("Invalid status transition: Task is not in PENDING status");
            }
        } else if (newStatus == ActivityStatus.COMPLETED) {
            if (task.getActivityStatus() == ActivityStatus.IN_PROGRESS || task.getActivityStatus() == ActivityStatus.PENDING) {
                task.setActivityStatus(ActivityStatus.COMPLETED);
                task.setCompletedAt(Timestamp.valueOf(LocalDateTime.now()));
                return taskDao.save(task);
            } else {
                throw new CustomAppException("Invalid status transition: Task is not in IN_PROGRESS or PENDING status");
            }
        } else if (newStatus == ActivityStatus.PENDING) {
            if (task.getActivityStatus() == ActivityStatus.COMPLETED) {
                task.setActivityStatus(ActivityStatus.PENDING);
                task.setCompletedAt(null);
                return taskDao.save(task);
            } else {
                throw new CustomAppException("Invalid status transition: Task is not in COMPLETED status");
            }
        }

        throw new CustomAppException("Invalid status transition: Unsupported status provided");
    }

    @Override
    public void deleteTask(Long taskId, Long userId) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));

        Optional<Task> obtainTask = taskDao.findTasksByUserAndId(user, taskId);
        if (obtainTask.isPresent()) {
            taskDao.deleteById(taskId);
        } else {
            throw new CustomAppException("Task doesn't exist");
        }
    }

    @Override
    public Task editTask(Long userId, Long taskId, TaskDto taskDto) {
        User user = userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User doesn't exist"));

        Task task = taskDao.findTasksByUserAndId(user, taskId)
                .orElseThrow(() -> new CustomAppException("Task doesn't exist"));

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setActivityStatus(taskDto.getActivityStatus());
        task.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return taskDao.save(task);
    }


}
