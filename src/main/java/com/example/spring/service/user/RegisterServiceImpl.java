package com.example.spring.service.user;

import com.example.spring.dto.user.UserRegistrationRequestDto;
import com.example.spring.dto.user.UserResponseDto;
import com.example.spring.exception.RegisterException;
import com.example.spring.mapper.UserMapper;
import com.example.spring.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(
            UserRegistrationRequestDto request) throws RegisterException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegisterException("User with email "
                    + request.getEmail() + " already exists.");
        }
        return userMapper.toDto(userRepository.save(userMapper.toModel(request)));
    }
}
