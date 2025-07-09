package com.realproj.tasklist.repository;

import com.realproj.tasklist.domain.user.Role;
import com.realproj.tasklist.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    void update(User user);
    void create(User user);
    void inserUserRole(Long userId, Role role);
    boolean isTaskOwner(Long userId, Long taskId);
    long delete(Long id);
}
