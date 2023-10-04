package com.techeer.fashioncloud.global.util.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum> {
    private ValidEnum annotation;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        boolean result = false;

        List<Object> validEnumValues = Arrays.stream(this.annotation.enumClass().getEnumConstants())
                .map(e -> (Object) e)
                .toList();

        return validEnumValues.contains(value);
    }
}