package com.example.spring.controller;

import com.example.spring.dto.user.UserRegistrationRequestDto;
import com.example.spring.dto.user.UserResponseDto;
import com.example.spring.exception.RegisterException;
import com.example.spring.service.user.RegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User Management", description = "Endpoints for managing user authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthenticationController {
    private final RegisterService registerService;

    @PostMapping({"/register"})
    @Operation(summary = "Create a new user", description = "Create a new user")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto request) throws RegisterException {
        return registerService.register(request);
    }
}
