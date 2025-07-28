package com.realproj.tasklist.web.controller;

import com.realproj.tasklist.domain.task.Task;
import com.realproj.tasklist.service.TaskService;
import com.realproj.tasklist.web.dto.task.TaskDto;
import com.realproj.tasklist.web.dto.validation.OnUpdate;
import com.realproj.tasklist.web.mappers.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor //задает конструктор для всех final полей
@Validated //включение валидации параметров
@Tag(
        name = "Task Controller",
        description = "Task API"
)
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PutMapping //по базовому пути контроллера
    @Operation(summary = "Update task")
    @PreAuthorize("canAccessTask(#dto.id)")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto dto){
        Task task  = taskMapper.toEntity(dto);
        Task updatedTask = taskService.update(task);
        return taskMapper.toDto(updatedTask);
    }

    @GetMapping("/{id}") //обрабатывает GET-запросы по пути /api/v1/tasks/{id}
    @Operation(summary = "Get TaskDto by id")
    @PreAuthorize("canAccessTask(#id)")
    public TaskDto getById(@PathVariable Long id){
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    @PreAuthorize("canAccessTask(#id)")
    public void deleteById(@PathVariable Long id){
        taskService.delete(id);
    }

}
