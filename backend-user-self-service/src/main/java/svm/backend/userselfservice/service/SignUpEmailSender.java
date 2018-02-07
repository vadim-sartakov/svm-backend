package svm.backend.userselfservice.service;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriTemplate;

@Service
public class SignUpEmailSender {
        
    private final Logger logger = LoggerFactory.getLogger(SignUpEmailSender.class);
         
    @Autowired private JavaMailSender mailSender;
    
    @Autowired
    @Qualifier("confirmMailTemplate")
    private SimpleMailMessage confirmMailTemplate;
    
    @Autowired
    @Qualifier("restoreMailTemplate")
    private SimpleMailMessage restoreMailTemplate;
    
    @Value("${svm.backend.userselfservice.controller.sign-up-url}")
    private String signUpUrl;
    
    @Value("${svm.backend.userselfservice.controller.restore-url}")
    private String restoreUrl;
    
    private void sendEmail(SimpleMailMessage message) {
        try {
            mailSender.send(message);
            logger.info("Message to {} has been sent successfully", message.getTo()[0]); 
        } catch(MailException e) {
            logger.error("Failed to send email to " + message.getTo()[0], e);
            throw e;
        }
        
    }
    
    public void sendActivateEmail(String email, HttpServletRequest request, String temporalPassword) {
                
        SimpleMailMessage message = new SimpleMailMessage(confirmMailTemplate);
        
        String activateLink = getActivateLink(request, temporalPassword);
        String text = message.getText();
        message.setText(String.format(text, activateLink));
        sendEmail(message);
        
    }
        
    protected String getActivateLink(HttpServletRequest request, String temporalPassword) {
        UriTemplate uriTemplate = new UriTemplate(getURL(request) + "/{baseUrl}/confirm{?id}");
        uriTemplate.expand(signUpUrl, temporalPassword);
        return uriTemplate.toString();
    }
        
    private StringBuffer getURL(HttpServletRequest request) {
        
        StringBuffer url = request.getRequestURL();
        String convertedUrl = url.toString();
        url.delete(convertedUrl.lastIndexOf(request.getRequestURI()), convertedUrl.length());
        
        return url;
        
    }
    
    public void sendRestoreEmail(String email, HttpServletRequest request, String temporalPassword) {
        
        SimpleMailMessage message = new SimpleMailMessage(restoreMailTemplate);
        
        String activateLink = getActivateLink(request, temporalPassword);
        String text = message.getText();
        message.setText(String.format(text, activateLink));
        sendEmail(message);

    }
    
    protected String getRestoreLink(HttpServletRequest request, String temporalPassword) {
        UriTemplate uriTemplate = new UriTemplate(getURL(request) + "/{baseUrl}/confirm{?id}");
        uriTemplate.expand(restoreUrl, temporalPassword);
        return uriTemplate.toString();
    }
    
}
