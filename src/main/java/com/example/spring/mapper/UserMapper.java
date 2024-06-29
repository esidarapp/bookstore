package com.example.spring.mapper;

import com.example.spring.config.MapperConfig;
import com.example.spring.dto.user.UserRegistrationRequestDto;
import com.example.spring.dto.user.UserResponseDto;
import com.example.spring.model.User;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserResponseDto toDto(User user);

    User toModel(UserRegistrationRequestDto requestDto);
}
