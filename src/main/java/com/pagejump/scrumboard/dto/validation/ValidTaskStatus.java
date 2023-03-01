package com.pagejump.scrumboard.dto.validation;

import com.pagejump.scrumboard.dto.validation.validator.ValidTaskStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Null;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidTaskStatus.List.class)
@Documented
@Constraint(
        validatedBy = {
                ValidTaskStatusValidator.class
        }
)
public @interface ValidTaskStatus {
    String message() default "{jakarta.validation.constraints.ValidTaskStatus.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        ValidTaskStatus[] value();
    }
}
