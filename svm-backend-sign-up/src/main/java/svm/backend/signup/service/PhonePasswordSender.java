package svm.backend.signup.service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import javax.ws.rs.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.dao.entity.user.account.UserAccount;
import svm.backend.signup.dao.repository.TemporalPasswordRepository;
import svm.backend.signup.dao.repository.UserAccountRepository;
import svm.backend.sms.SmsMessage;
import svm.backend.sms.SmsSender;

public class PhonePasswordSender {
    
    protected final Logger logger = LoggerFactory.getLogger(PhonePasswordSender.class);
    
    @Value("${svm.backend.signup.PhonePassword.expires-in:180}")
    protected int expiresIn;
    
    @Autowired protected MessageSource messageSource;
    @Autowired protected SmsSender smsSender;
    @Autowired protected TemporalPasswordRepository passwordRepository;
    @Autowired protected UserAccountRepository userAccountRepository;
    @Autowired protected PhonePasswordGenerator phonePasswordGenerator;
    @Autowired protected PasswordEncoder passwordEncoder;
    @Autowired protected MessageSource mesasgeSource;
    
    public void sendMessage(String phoneNumber) {

        UserAccount account = userAccountRepository.findByAccountIgnoreCase(phoneNumber);
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
        SmsMessage message = new SmsMessage(phoneNumber, text);
        smsSender.send(message);
                
        logger.info("Created phone password {} for {}",
                phonePassword.getPassword(),
                phonePassword.getUserAccount()
        );
        
    }
    
}
