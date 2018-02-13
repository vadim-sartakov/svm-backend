package svm.backend.signup.controller;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import javax.validation.constraints.Pattern;
import javax.ws.rs.BadRequestException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.dao.entity.user.account.UserAccount;
import svm.backend.signup.dao.entity.validator.RegexPatterns;
import svm.backend.signup.dao.repository.TemporalPasswordRepository;
import svm.backend.signup.dao.repository.UserAccountRepository;
import svm.backend.signup.service.PhonePasswordGenerator;
import svm.backend.sms.SmsMessage;
import svm.backend.sms.SmsSender;

public abstract class PhoneAccountController {
    
    protected final Logger logger = LoggerFactory.getLogger(PhoneAccountController.class);
    
    @Value("${svm.backend.signup.PhonePassword.expires-in:180}")
    protected int expiresIn;
    
    @Autowired protected MessageSource messageSource;
    @Autowired protected SmsSender smsSender;
    @Autowired protected TemporalPasswordRepository passwordRepository;
    @Autowired protected UserAccountRepository userAccountRepository;
    @Autowired protected PhonePasswordGenerator phonePasswordGenerator;
    @Autowired protected PasswordEncoder passwordEncoder;
    @Autowired protected MessageSource mesasgeSource;
    
    protected void sendMessage(Request passwordRequest) {

        UserAccount account = userAccountRepository.findByAccountIgnoreCase(passwordRequest.getPhoneNumber());
        TemporalPassword alreadyCreatedPassword = passwordRepository.findByUserAccount(account);
        if (alreadyCreatedPassword != null) {
            
            long secondsLeft = ChronoUnit.SECONDS.between(ZonedDateTime.now(),
                    alreadyCreatedPassword.getExpiresAt().plusSeconds(1));
            
            if (secondsLeft > 0) {
                
                String message = messageSource.getMessage(
                    "svm.backend.signup.controller.PhonePasswordController.waitMessage",
                    new Object[] { secondsLeft },
                    LocaleContextHolder.getLocale());
            
                throw new BadRequestException(message);
                
            }
                        
        }
        
        String generatedPassword = phonePasswordGenerator.generate();
        
        TemporalPassword phonePassword = new TemporalPassword();
        phonePassword.setCreatedAt(ZonedDateTime.now());
        phonePassword.setUserAccount(account);
        phonePassword.setPassword(passwordEncoder.encode(generatedPassword));
        phonePassword.setExpiresAt(ZonedDateTime.now().plusSeconds(expiresIn));
        
        passwordRepository.save(phonePassword);
        
        String text = mesasgeSource.getMessage(
                "svm.backend.signup.controller.PhonePasswordController.message",
                new Object[] { generatedPassword },
                LocaleContextHolder.getLocale()
        );
        SmsMessage message = new SmsMessage(passwordRequest.phoneNumber, text);
        smsSender.send(message);
                
        logger.info("Created phone password {} for {}",
                phonePassword.getPassword(),
                phonePassword.getUserAccount()
        );
        
    }
        
    @AllArgsConstructor
    @Data
    public static class Request {
        @NotEmpty
        @Pattern(
                regexp = RegexPatterns.MOBILE_PHONE_PATTERN,
                message = RegexPatterns.WRONG_MOBILE_PHONE_MESSAGE
        )
        private String phoneNumber;
    }
    
}
