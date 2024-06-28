package com.example.spring.validate;

import com.example.spring.validate.annotations.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;
import org.springframework.beans.BeanWrapperImpl;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        final Object field = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
        final Object fieldMatch = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);
        return Objects.equals(field, fieldMatch);
    }
}
