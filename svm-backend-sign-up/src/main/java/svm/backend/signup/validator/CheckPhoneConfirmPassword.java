package svm.backend.signup.validator;

import java.time.ZonedDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import svm.backend.signup.controller.confirm.PhoneConfirmRequest;
import svm.backend.signup.dao.entity.QTemporalPassword;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.dao.repository.TemporalPasswordRepository;

public class CheckPhoneConfirmPassword implements ConstraintValidator<PhoneConfirmPassword, PhoneConfirmRequest> {

    @Value("${svm.backend.signup.validator.CheckConfirmPassword.maxAttempts:3}")
    private int maxAttempts;
    
    @Autowired private TemporalPasswordRepository passwordRepository;
    
    @Override
    public void initialize(PhoneConfirmPassword constraintAnnotation) {
        
    }

    @Override
    public boolean isValid(PhoneConfirmRequest confirmRequest, ConstraintValidatorContext context) {
                
        boolean isValid = true; 
        
        String password = confirmRequest.getTemporalPassword();
        if (password == null || password.isEmpty())
            return isValid;
        
        TemporalPassword phonePassword = passwordRepository.findOne(
                QTemporalPassword.temporalPassword.userAccount.account.equalsIgnoreCase(confirmRequest.getPhoneNumber())
        );
        
        if (phonePassword == null ||
                phonePassword.getExpiresAt().isBefore(ZonedDateTime.now()) ||
                phonePassword.getAttempts() >= maxAttempts) {
            isValid = false;            
        } else if (!phonePassword.getPassword().equals(password)) {
            isValid = false;
            phonePassword.setAttempts(phonePassword.getAttempts() + 1);
            passwordRepository.save(phonePassword);
        }
        
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("temporalPassword")
                    .addConstraintViolation();
        }
        
        return isValid;
                
    }

}
