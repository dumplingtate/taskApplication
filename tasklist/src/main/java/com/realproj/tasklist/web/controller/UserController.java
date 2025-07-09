package com.realproj.tasklist.web.controller;


import com.realproj.tasklist.domain.task.Task;
import com.realproj.tasklist.domain.user.User;
import com.realproj.tasklist.service.TaskService;
import com.realproj.tasklist.service.UserService;
import com.realproj.tasklist.web.dto.task.TaskDto;
import com.realproj.tasklist.web.dto.user.UserDto;
import com.realproj.tasklist.web.dto.validation.OnUpdate;
import com.realproj.tasklist.web.mappers.TaskMapper;
import com.realproj.tasklist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor //задает конструктор для всех final полей
@Validated //включение валидации параметров
public class UserController {
    private final UserService userService;
    private final TaskService taskService;

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping //по базовому пути контроллера
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto){
        User user = userMapper.toEntity(dto);
        User updateUser = userService.update(user);
        return userMapper.toDto(updateUser);
    }

    @GetMapping("/{id}") //обрабатывает GET-запросы по пути /api/v1/users/{id}
    public UserDto getById(@PathVariable Long id){
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    public List<TaskDto> getTasksByUserId(@PathVariable Long id){
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    public TaskDto createTask(@PathVariable Long id,
                              @Validated(OnUpdate.class) @RequestBody TaskDto dto){
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }
}
