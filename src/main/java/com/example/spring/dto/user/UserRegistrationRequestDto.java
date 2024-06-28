package com.example.spring.dto.user;

import com.example.spring.validate.annotations.FieldMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private String password;
    @Length(min = 8)
    private String repeatPassword;
    @NotEmpty
    @NotNull
    private String firstName;
    @NotEmpty
    @NotNull
    private String lastName;
    private String shippingAddress;
}
