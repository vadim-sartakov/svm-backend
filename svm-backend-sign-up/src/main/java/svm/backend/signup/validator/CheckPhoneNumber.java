package svm.backend.signup.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPhoneNumber implements ConstraintValidator<PhoneNumber, String> {

    public static final String MOBILE_PHONE_PATTERN = "^\\+\\d{1,2}\\(\\d{3}\\)\\d{3}\\-\\d{2}\\-\\d{2}$";
    
    @Override
    public void initialize(PhoneNumber constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(MOBILE_PHONE_PATTERN);
    }
    
}
