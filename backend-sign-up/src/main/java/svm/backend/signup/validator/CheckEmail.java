package svm.backend.signup.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckEmail implements ConstraintValidator<Email, String> {

    public static final String EMAIL_PATTERN = "^([A-Za-z0-9_\\.-]+\\@[\\dA-Za-z\\.-]+\\.[a-z\\.]{2,6})$";
    
    @Override
    public void initialize(Email constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(EMAIL_PATTERN);
    }
    
}
