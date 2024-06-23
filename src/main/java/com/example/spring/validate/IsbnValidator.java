package com.example.spring.validate;

import com.example.spring.validate.annotations.Isbn;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsbnValidator implements ConstraintValidator<Isbn,String> {
    private static final String ISBN_PATTERN = "^97[89]-\\d{1,5}-\\d{1,7}-\\d{1,7}-\\d$";

    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        return isbn != null && Pattern.compile(ISBN_PATTERN).matcher(isbn).matches();
    }
}
