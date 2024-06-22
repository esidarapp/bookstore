package com.example.spring.validate.annotations;

import com.example.spring.validate.CoverImageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CoverImageValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CoverImage {
    String message() default "Invalid cover image URL format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
