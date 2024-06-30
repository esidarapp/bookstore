package com.example.spring.validate;

import com.example.spring.validate.annotations.CoverImage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CoverImageValidator implements ConstraintValidator<CoverImage, String> {
    private static final String IMAGE_URL_PATTERN = "^(https?|ftp)://.*(jpeg|jpg|gif|png)$";

    @Override
    public boolean isValid(String coverImage, ConstraintValidatorContext context) {
        if (coverImage == null) {
            return true;
        }
        return Pattern.compile(IMAGE_URL_PATTERN).matcher(coverImage).matches();
    }
}
