package com.works.customValids;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class StatusConstValid implements ConstraintValidator<StatusValid, String> {

    String[] statusArr = {"customer", "user"};

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Arrays.asList(statusArr).contains(value);
    }

}
