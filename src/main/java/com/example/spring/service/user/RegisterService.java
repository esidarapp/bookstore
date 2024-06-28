package com.example.spring.service.user;

import com.example.spring.dto.user.UserRegistrationRequestDto;
import com.example.spring.dto.user.UserResponseDto;
import com.example.spring.exception.RegisterException;

public interface RegisterService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegisterException;
}
