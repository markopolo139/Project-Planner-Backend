package pl.ms.projectoverview.web.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pl.ms.projectoverview.app.entitites.ProjectStatus;

public class ProjectStatusValidatorImpl implements ConstraintValidator<ProjectStatusValidator, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            ProjectStatus.valueOf(value);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
