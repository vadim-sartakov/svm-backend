package svm.backend.signup.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.ZonedDateTime;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import svm.backend.security.dao.repository.UserRepository;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.dao.entity.user.account.UserAccount;
import svm.backend.signup.dao.repository.TemporalPasswordRepository;
import svm.backend.signup.dao.repository.UserAccountRepository;
import svm.backend.web.utils.WebUtils;

public class EmailPasswordSender {
    
    protected final Logger logger = LoggerFactory.getLogger(EmailPasswordSender.class);
    
    @Value("${svm.backend.signup.EmailPassword.expires-in:86400}")
    protected int expiresIn;
        
    @Value("${spring.mail.username}")
    protected String from;
    
    @Autowired protected JavaMailSender mailSender;
    @Autowired protected UserAccountRepository accountRepository;
    @Autowired protected TemporalPasswordRepository passwordRepository;
    @Autowired protected EmailPasswordGenerator emailPasswordGenerator;
    @Autowired protected PasswordEncoder passwordEncoder;
    @Autowired protected MessageSource messageSource;
    @Autowired protected UserRepository userRepository;
    
    public void sendMessage(String email,
            String urlLink,
            String subjectMessageId,
            String textMessageId,
            HttpServletRequest request) {
        
        String baseUrl = WebUtils.getBaseURL(request);
        UserAccount account = accountRepository.findByAccountIgnoreCase(email);
                            
        String generatedPassword = emailPasswordGenerator.generate();
        
        TemporalPassword emailPassword = new TemporalPassword();
        emailPassword.setPassword(passwordEncoder.encode(generatedPassword));
        emailPassword.setUserAccount(account);
        emailPassword.setCreatedAt(ZonedDateTime.now());
        emailPassword.setExpiresAt(
                emailPassword.getCreatedAt().plusSeconds(expiresIn)
        );
        
        passwordRepository.save(emailPassword);
        
        String link;
        try {
            link = baseUrl +
                    "/" +
                    urlLink +
                    "/confirm?id=" +
                    URLEncoder.encode(emailPassword.getPassword(), "UTF-8");
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        
        String subject = messageSource.getMessage(
                subjectMessageId,
                null,
                LocaleContextHolder.getLocale());
        
        String text = messageSource.getMessage(
                textMessageId,
                new Object[] { link },
                LocaleContextHolder.getLocale());
                
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setFrom(from);
        message.setTo(email);
        message.setText(text);
        mailSender.send(message);
                        
        logger.info("Message to {} has been sent successfully", email);
        
    }
    
}
