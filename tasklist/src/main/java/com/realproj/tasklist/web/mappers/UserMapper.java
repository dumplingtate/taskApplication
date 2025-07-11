package com.realproj.tasklist.web.mappers;

import com.realproj.tasklist.domain.user.User;
import com.realproj.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);

}
