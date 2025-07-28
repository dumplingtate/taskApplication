package com.realproj.tasklist.repository.mappers;

import com.realproj.tasklist.domain.task.Status;
import com.realproj.tasklist.domain.task.Task;
import com.realproj.tasklist.domain.user.Role;
import com.realproj.tasklist.domain.user.User;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRowMapper {


    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        Set<Role> roles = new HashSet<>();
        List<Task> tasks = new ArrayList<>();
        User user = null;

        while (resultSet.next()) {
            if (user == null) {
                user = new User();
                user.setId(resultSet.getLong("user_id"));
                user.setName(resultSet.getString("user_name"));
                user.setUsername(resultSet.getString("user_username"));
                user.setPassword(resultSet.getString("user_password"));
            }
            String roleName = resultSet.getString("user_role_role");
            System.out.println("Найдена роль: " + roleName);
            roles.add(Role.valueOf(roleName));

        }

        if (user != null) {
            user.setRoles(roles);
            user.setTasks(tasks);  // если есть задачи, нужно добавить логику заполнения
            System.out.println("User собран: " + user.getUsername() + ", роли: " + roles);
        } else {
            System.out.println("Пользователь не найден в resultSet");
        }

        return user;
    }

}
