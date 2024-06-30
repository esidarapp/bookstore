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
    @Length(min = 3, max = 255)
    @NotBlank
    private String password;
    @NotBlank
    @Length(min = 3, max = 255)
    private String repeatPassword;
    @Length(max = 255)
    @NotBlank
    private String firstName;
    @Length(max = 255)
    @NotBlank
    private String lastName;
    @Length(max = 255)
    private String shippingAddress;
}
