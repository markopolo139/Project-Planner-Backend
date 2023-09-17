package pl.ms.projectoverview.web.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ProjectStatusValidatorImpl.class)
public @interface ProjectStatusValidator {
    String message() default "Invalid project status value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
