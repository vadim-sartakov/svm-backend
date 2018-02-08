package svm.backend.signup.controller;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.BadRequestException;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.security.dao.entity.validator.RegexPatterns;
import svm.backend.signup.dao.entity.PhonePassword;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.dao.repository.TemporalPasswordRepository;
import svm.backend.signup.service.PhonePasswordGenerator;
import svm.backend.sms.SmsMessage;
import svm.backend.sms.SmsSender;

@RestController
@RequestMapping("${svm.backend.signup.controller.temporal-password-url}/phone")
public class PhonePasswordController {
    
    private final Logger logger = LoggerFactory.getLogger(PhonePasswordController.class);
    
    @Value("${svm.backend.signup.PhonePassword.expiresIn:180}")
    private int expiresIn;
    
    @Autowired private MessageSource messageSource;
    @Autowired private SmsSender smsSender;
    @Autowired private TemporalPasswordRepository passwordRepository;
    @Autowired private PhonePasswordGenerator phonePasswordGenerator;
    @Autowired private SmsMessage smsTemplate;
    
    @PostMapping
    @Transactional
    public void addPhonePassword(@RequestBody @Valid PhonePasswordRequest passwordRequest) {

        TemporalPassword alreadyCreatedPassword = passwordRepository.findByAccountIgnoreCase(passwordRequest.getPhoneNumber());
        
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
        
        PhonePassword phonePassword = new PhonePassword();
        phonePassword.setCreatedAt(ZonedDateTime.now());
        phonePassword.setAccount(passwordRequest.getPhoneNumber());
        phonePassword.setPassword(generatedPassword);
        phonePassword.setExpiresAt(ZonedDateTime.now().plusSeconds(expiresIn));
        
        passwordRepository.save(phonePassword);
        
        String text = String.format(smsTemplate.getText(), generatedPassword);
        smsTemplate.setPhoneNumber(passwordRequest.getPhoneNumber());
        smsTemplate.setText(text);
        smsSender.send(smsTemplate);
                
        logger.info("Created phone password {} for {}",
                phonePassword.getPassword(),
                phonePassword.getAccount());
        
    }
    
    @Data
    public static class PhonePasswordRequest {
        @NotEmpty
        @Pattern(
                regexp = RegexPatterns.MOBILE_PHONE_PATTERN,
                message = RegexPatterns.WRONG_MOBILE_PHONE_MESSAGE
        )
        private String phoneNumber;
    }
    
}
