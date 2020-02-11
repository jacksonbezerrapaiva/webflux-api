package com.project.webflux.mapper;

import com.project.webflux.dto.UserDto;
import com.project.webflux.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
