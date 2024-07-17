package com.example.spring.service.user;

import com.example.spring.dto.user.UserRegistrationRequestDto;
import com.example.spring.dto.user.UserResponseDto;
import com.example.spring.exception.RegisterException;
import com.example.spring.model.User;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegisterException;

    User findById(Long userId);

}
