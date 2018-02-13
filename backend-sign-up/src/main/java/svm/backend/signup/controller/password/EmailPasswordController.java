package svm.backend.signup.controller.password;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.ZonedDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.signup.dao.entity.validator.RegexPatterns;
import svm.backend.signup.dao.repository.UserAccountRepository;
import svm.backend.signup.dao.entity.EmailPassword;
import svm.backend.signup.dao.entity.user.account.UserAccount;
import svm.backend.signup.dao.repository.EmailPasswordRepository;
import svm.backend.signup.service.EmailPasswordGenerator;
import svm.backend.web.utils.WebUtils;

@RestController
@RequestMapping("${svm.backend.signup.controller.password-url}/email")
public class EmailPasswordController {
    
    private final Logger logger = LoggerFactory.getLogger(EmailPasswordController.class);
    
    @Value("${svm.backend.signup.EmailPassword.expires-in:86400}")
    private int expiresIn;
    
    @Value("${svm.backend.signup.controller.sign-up-url}")
    private String signUpUrl;
    
    @Value("${svm.backend.signup.controller.restore-url}")
    private String restoreUrl;
    
    @Value("${spring.mail.username}")
    private String from;
    
    @Autowired private JavaMailSender mailSender;
    @Autowired private UserAccountRepository accountRepository;
    @Autowired private EmailPasswordRepository passwordRepository;
    @Autowired private EmailPasswordGenerator emailPasswordGenerator;
    @Autowired private MessageSource messageSource;
        
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendPasswordEmail(@RequestBody @Valid PasswordRequest passwordRequest, HttpServletRequest request) {
        sendPasswordEmail(passwordRequest, WebUtils.getBaseURL(request));
    }
    
    public void sendPasswordEmail(PasswordRequest passwordRequest, String baseUrl) {
        
        String email = passwordRequest.email;
        UserAccount account = accountRepository.findByAccountIgnoreCase(email);
                
        if (account == null && passwordRequest.type.equals(PasswordRequest.Type.RESTORE)) {
            logger.warn("Attempt to restore password with wrong login {}", email);
            return;
        }
            
        String generatedPassword = emailPasswordGenerator.generate();
        
        EmailPassword emailPassword = new EmailPassword();
        emailPassword.setPassword(generatedPassword);
        emailPassword.setAccount(email);
        emailPassword.setCreatedAt(ZonedDateTime.now());
        emailPassword.setExpiresAt(
                emailPassword.getCreatedAt().plusSeconds(expiresIn)
        );
        
        passwordRepository.save(emailPassword);
        
        String link;
        try {
            link = baseUrl +
                    "/" +
                    (passwordRequest.type.equals(PasswordRequest.Type.ACTIVATE) ? signUpUrl : restoreUrl) +
                    "/confirm?id=" +
                    URLEncoder.encode(generatedPassword, "UTF-8");
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        
        String subject = messageSource.getMessage(
                "svm.backend.signup.controller.EmailPasswordController." +
                        (passwordRequest.type.equals(PasswordRequest.Type.ACTIVATE) ? "activateSubject" : "restoreSubject"),
                null,
                LocaleContextHolder.getLocale());
        
        String text = messageSource.getMessage(
                "svm.backend.signup.controller.EmailPasswordController." +
                        (passwordRequest.type.equals(PasswordRequest.Type.ACTIVATE) ? "activateMessage" : "restoreMessage"),
                new Object[] { link },
                LocaleContextHolder.getLocale());
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setFrom(from);
        message.setTo(email);
        message.setText(text);
        mailSender.send(message);
                        
        logger.info("Message to {} has been sent", email);
        
    }
    
    @AllArgsConstructor
    public static class PasswordRequest {
        
        @NotEmpty
        @Pattern(regexp = RegexPatterns.EMAIL_PATTERN,
                message = RegexPatterns.WRONG_EMAIL_MESSAGE)
        private String email;
        
        @NotNull
        private Type type;
        
        public static enum Type {
            ACTIVATE,
            RESTORE
        }
        
    }
    
}
