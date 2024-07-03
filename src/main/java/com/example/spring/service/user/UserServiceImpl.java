package com.example.spring.service.user;

import com.example.spring.dto.user.UserRegistrationRequestDto;
import com.example.spring.dto.user.UserResponseDto;
import com.example.spring.exception.EntityNotFoundException;
import com.example.spring.exception.RegisterException;
import com.example.spring.mapper.UserMapper;
import com.example.spring.model.Role;
import com.example.spring.model.User;
import com.example.spring.repository.role.RoleRepository;
import com.example.spring.repository.user.UserRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(
            UserRegistrationRequestDto request) throws RegisterException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegisterException("User with email "
                    + request.getEmail() + " already exists.");
        }
        User user = userMapper.toModel(request);
        Role userRole = roleRepository.findByName(Role.RoleName.ROLE_USER)
                .orElseThrow(() -> new EntityNotFoundException("Role USER not found"));
        user.setRoles(Set.of(userRole));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }
}
