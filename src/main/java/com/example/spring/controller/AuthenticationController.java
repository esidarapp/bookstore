package com.example.spring.controller;

import com.example.spring.dto.user.UserLoginRequestDto;
import com.example.spring.dto.user.UserLoginResponseDto;
import com.example.spring.dto.user.UserRegistrationRequestDto;
import com.example.spring.dto.user.UserResponseDto;
import com.example.spring.exception.RegisterException;
import com.example.spring.security.AuthenticationService;
import com.example.spring.service.user.UserService;
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
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping({"/register"})
    @Operation(summary = "Create a new user", description = "Create a new user")
    public UserResponseDto register(
            @RequestBody @Valid UserRegistrationRequestDto request) throws RegisterException {
        return userService.register(request);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
