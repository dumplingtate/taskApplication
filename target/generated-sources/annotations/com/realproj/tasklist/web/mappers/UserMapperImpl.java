package com.realproj.tasklist.web.mappers;

import com.realproj.tasklist.domain.user.User;
import com.realproj.tasklist.web.dto.user.UserDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-11T21:39:53+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 24 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        return userDto;
    }

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        return user;
    }
}
