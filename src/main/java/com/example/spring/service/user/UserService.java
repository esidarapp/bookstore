package com.example.spring.service.user;

import com.example.spring.dto.user.UserRegistrationRequestDto;
import com.example.spring.dto.user.UserResponseDto;
import com.example.spring.exception.RegisterException;
import java.util.List;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegisterException;

    List<UserResponseDto> findAllUsers();
}
