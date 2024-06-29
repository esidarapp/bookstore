package com.example.spring.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String shippingAddress;
}
