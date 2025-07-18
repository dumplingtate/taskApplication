package com.realproj.tasklist.repository.impl;

import com.realproj.tasklist.domain.exception.ResourceMappingException;
import com.realproj.tasklist.domain.user.Role;
import com.realproj.tasklist.domain.user.User;
import com.realproj.tasklist.repository.DataSourceConfig;
import com.realproj.tasklist.repository.UserRepository;
import com.realproj.tasklist.repository.mappers.UserRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {


    private final DataSourceConfig dataSourceConfig;

    private final String  FIND_BY_ID = """
            SELECT u.id as user_id,
                        u.name as user_name,
                        u.password as user_password,
                        u.username as user_username,
                        ur.role as user_role_role,
                        t.id as task_id,
                        t.title as task_title,
                        t.description as task_description,
                        t.expiration_date as task_expiration_date,
                        t.status as task_status
                        FROM users u
            LEFT JOIN users_roles ur on u.id = ur.user_id
            LEFT JOIN user_tasks ut on u.id = ut.user_id
            LEFT JOIN tasks t on ut.task_id = t.id
            WHERE u.id = :id
            """;

    private final String  FIND_BY_USERNAME = """
            SELECT u.id as user_id,
                        u.name as user_name,
                        u.password as user_password,
                        u.username as user_username,
                        ur.role as user_role_role,
                        t.id as task_id,
                        t.title as task_title,
                        t.description as task_description,
                        t.expiration_date as task_expiration_date,
                        t.status as task_status
                        FROM users u
            LEFT JOIN users_roles ur on u.id = ur.user_id
            LEFT JOIN user_tasks ut on u.id = ut.user_id
            LEFT JOIN tasks t on ut.task_id = t.id
            WHERE u.username = :username
            """;

    private final String UPDATE = """
            UPDATE users
            SET name = :name,
            username = :username,
            password = :password,
            WHERE id = :id
            """;

    private final String CREATE = """
            INSERT INTO users (name, username, password)
            VALUES (:name, :username: :password)
            """;

    private final String INSERT_USER_ROLE = """
            INSERT INTO users_roles (role)
            VALUES (:role)
            """;

    private final String IS_TASK_OWNER= """
            SELECT exists(
            SELECT 1
            FROM user_tasks
            WHERE user_id = :user_id
            AND task_id = :task_id
            )
            """;

    private final String DELETE= """
            DELETE FROM users
            WHERE id = :id
            """;


    @Override
    public Optional<User> findById(Long id) {
        try{
            Connection connection = dataSourceConfig.getConection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setLong(1, id);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch(SQLException throwables){
            throw  new ResourceMappingException("Error while finding by id");
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try{
            Connection connection = dataSourceConfig.getConection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USERNAME, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, username);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                return Optional.ofNullable(UserRowMapper.mapRow(resultSet));
            }
        } catch(SQLException throwables){
            throw  new ResourceMappingException("Error while finding by username");
        }
    }

    @Override
    public void update(User user) {
        try{
            Connection connection = dataSourceConfig.getConection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            try(ResultSet resultSet = preparedStatement.executeQuery()){

            }
        } catch(SQLException throwables){
            throw  new ResourceMappingException("Error while updating");
        }
    }

    @Override
    public void create(User user) {
        try{
            Connection connection = dataSourceConfig.getConection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUsername());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.executeUpdate();
            try(ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                resultSet.next();
                user.setId(resultSet.getLong(1));
            }
            try(ResultSet resultSet = preparedStatement.executeQuery()){

            }
        } catch(SQLException throwables){
            throw  new ResourceMappingException("Error while creating");
        }
    }

    @Override
    public void insertUserRole(Long userId, Role role) {
        try{
            Connection connection = dataSourceConfig.getConection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE);
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, role.name());
            preparedStatement.executeUpdate();
        } catch(SQLException throwables){
            throw  new ResourceMappingException("Error while inserting user role");
        }

    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        try{
            Connection connection = dataSourceConfig.getConection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, taskId);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                resultSet.next();
                return resultSet.getBoolean(1);
            }
        } catch(SQLException throwables){
            throw  new ResourceMappingException("Error while searching");
        }
    }

    @Override
    public void delete(Long id) {
        try{
            Connection connection = dataSourceConfig.getConection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch(SQLException throwables){
            throw  new ResourceMappingException("Error while inserting user role");
        }
    }
}
