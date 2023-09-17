package pl.ms.projectoverview.web.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidatorImpl.class)
public @interface MyPasswordValidator {
    String message() default "Invalid password (minLength - 5, no whitespaces," +
            " username can't be used as password, 1 uppercase, digit and special character)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
