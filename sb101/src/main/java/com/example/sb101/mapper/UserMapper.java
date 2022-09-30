package com.example.sb101.mapper;

import com.example.sb101.domain.User;
import com.example.sb101.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "emailId", target = "email")
    @Mapping(source = "contactNo", target = "phoneNo")
    User toEntity(UserDto userDto);

    @Mapping(source = "email", target = "emailId")
    @Mapping(source = "phoneNo", target = "contactNo")
    UserDto toDTO(User user);

    List<UserDto> toDTOList(List<User> users);

}
