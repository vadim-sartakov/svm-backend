package svm.backend.signup.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public abstract class CheckUniqueUser implements ConstraintValidator<UniqueUsername, String> {

    @Value("${svm.backend.security.findUserIgnoreCase:true}")
    protected boolean ignoreCase;
        
    @Override
    public void initialize(UniqueUsername annotation) {

    }
    
    protected boolean checkIsUnique(Object alreadyRegistered, String field, ConstraintValidatorContext context) {
        
        if (alreadyRegistered == null)
            return true;
        
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                .addPropertyNode(field)
                .addConstraintViolation();

        return false;
        
    }
    
}
