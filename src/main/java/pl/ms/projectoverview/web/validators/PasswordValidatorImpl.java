package pl.ms.projectoverview.web.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.List;

public class PasswordValidatorImpl implements ConstraintValidator<MyPasswordValidator, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(
                List.of(
                        new LengthRule(5, Integer.MAX_VALUE),
                        new WhitespaceRule(),
                        new UsernameRule(true, true),
                        new CharacterRule(EnglishCharacterData.UpperCase, 1),
                        new CharacterRule(EnglishCharacterData.Digit, 1),
                        new CharacterRule(EnglishCharacterData.Special, 1)
                )
        );

        RuleResult result = validator.validate(new PasswordData(value));

        return result.isValid();
    }
}
