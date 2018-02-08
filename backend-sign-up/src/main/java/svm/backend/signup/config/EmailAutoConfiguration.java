package svm.backend.signup.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import svm.backend.signup.service.EmailPasswordGenerator;
import svm.backend.signup.service.SimpleEmailPasswordGenerator;

@Configuration
@ConditionalOnMissingBean(EmailAutoConfiguration.class)
public class EmailAutoConfiguration {
    
    @Bean
    public EmailPasswordGenerator emailPasswordGenerator() {
        return new SimpleEmailPasswordGenerator();
    }
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SimpleMailMessage signUpTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("%s");
        return message;
    }
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SimpleMailMessage restoreTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("%s");
        return message;
    }
    
}
