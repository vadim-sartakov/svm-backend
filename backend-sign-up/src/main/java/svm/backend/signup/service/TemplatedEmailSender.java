package svm.backend.signup.service;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.util.UriTemplate;
import svm.backend.web.utils.WebUtils;

public class TemplatedEmailSender {
        
    private final Logger logger = LoggerFactory.getLogger(TemplatedEmailSender.class);
         
    @Autowired private JavaMailSender mailSender;
    
    @Autowired
    @Qualifier("signUpTemplate")
    private SimpleMailMessage signUpMailTemplate;
    
    @Autowired
    @Qualifier("restoreTemplate")
    private SimpleMailMessage restoreMailTemplate;
    
    @Value("${svm.backend.signup.controller.sign-up-url}")
    private String signUpUrl;
    
    @Value("${svm.backend.signup.controller.restore-url}")
    private String restoreUrl;
        
    public void sendSignUpEmail(String email, HttpServletRequest request, String temporalPassword) {
                
        SimpleMailMessage message = new SimpleMailMessage(signUpMailTemplate);
        message.setTo(email);
        
        String activateLink = getActivateLink(request, temporalPassword);
        String text = message.getText();
        message.setText(String.format(text, activateLink));
        sendEmail(message);
        
    }
    
    private void sendEmail(SimpleMailMessage message) {
        try {
            mailSender.send(message);
            logger.info("Message to {} has been sent successfully", message.getTo()[0]); 
        } catch(MailException e) {
            logger.error("Failed to send email to " + message.getTo()[0], e);
            throw e;
        }
    }
        
    private String getActivateLink(HttpServletRequest request, String temporalPassword) {
        UriTemplate uriTemplate = new UriTemplate(WebUtils.getBaseURL(request) + "/{baseUrl}/confirm{?id}");
        uriTemplate.expand(signUpUrl, temporalPassword);
        return uriTemplate.toString();
    }
    
    public void sendRestoreEmail(String email, HttpServletRequest request, String temporalPassword) {
        
        SimpleMailMessage message = new SimpleMailMessage(restoreMailTemplate);
        message.setTo(email);
        
        String activateLink = getRestoreLink(request, temporalPassword);
        String text = message.getText();
        message.setText(String.format(text, activateLink));
        sendEmail(message);

    }
    
    private String getRestoreLink(HttpServletRequest request, String temporalPassword) {
        UriTemplate uriTemplate = new UriTemplate(WebUtils.getBaseURL(request) + "/{baseUrl}/confirm{?id}");
        uriTemplate.expand(restoreUrl, temporalPassword);
        return uriTemplate.toString();
    }
    
}
