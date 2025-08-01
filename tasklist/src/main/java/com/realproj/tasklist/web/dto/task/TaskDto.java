package com.realproj.tasklist.web.dto.task;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.realproj.tasklist.domain.task.Status;
import com.realproj.tasklist.web.dto.validation.OnCreate;
import com.realproj.tasklist.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Schema(description = "Task DTO")
public class TaskDto {
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message="Title must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max=255, message = "Title length must be smaller than 255 symbols.", groups ={OnCreate.class, OnUpdate.class})
    private String title;

    @Length(max=255, message = "Description length must be smaller than 255 symbols.", groups ={OnCreate.class, OnUpdate.class})
    private String description;

    private Status status;

    @DateTimeFormat(iso=DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime expirationData;
}
