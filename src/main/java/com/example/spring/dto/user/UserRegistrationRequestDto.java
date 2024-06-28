package com.example.spring.dto.user;

import com.example.spring.validate.annotations.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@FieldMatch(first = "password", second = "repeatPassword")
public class UserRegistrationRequestDto {
    @Email
    private String email;
    @Length(min = 8)
    @NotBlank
    private String password;
    @NotBlank
    @Length(min = 8)
    private String repeatPassword;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String shippingAddress;
}
