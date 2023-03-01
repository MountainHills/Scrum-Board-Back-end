package com.pagejump.scrumboard.dto.validation.validator;

import com.pagejump.scrumboard.dto.validation.ValidTaskStatus;
import com.pagejump.scrumboard.model.enums.TaskStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.EnumUtils;

public class ValidTaskStatusValidator implements ConstraintValidator<ValidTaskStatus, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null
                && !s.isEmpty()
                && !s.isBlank()
                && EnumUtils.isValidEnum(TaskStatus.class, s.toUpperCase());
    }
}
