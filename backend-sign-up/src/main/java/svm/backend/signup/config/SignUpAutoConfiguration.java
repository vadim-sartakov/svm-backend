package svm.backend.signup.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import svm.backend.signup.controller.restore.EmailRestoreController;
import svm.backend.signup.controller.SignUpController;
import svm.backend.signup.controller.activate.EmailActivateController;
import svm.backend.signup.controller.activate.PhoneActivateController;
import svm.backend.signup.controller.restore.PhoneRestoreController;
import svm.backend.signup.service.SignUpUserFactory;
import svm.backend.signup.service.UserAccountUserDetailsService;

@Configuration
@ConditionalOnMissingBean(SignUpAutoConfiguration.class)
public class SignUpAutoConfiguration {
       
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new UserAccountUserDetailsService();
    }
    
    @Bean
    public SignUpController signUpController() {
        return new SignUpController();
    }
        
    @Bean
    public EmailActivateController emailActivateController() {
        return new EmailActivateController();
    }
    
    @Bean
    public PhoneActivateController phoneActivateController() {
        return new PhoneActivateController();
    }
    
    @Bean
    public EmailRestoreController emailRestoreController() {
        return new EmailRestoreController();
    }
    
    @Bean
    public PhoneRestoreController phoneRestoreController() {
        return new PhoneRestoreController();
    }
    
    @Bean
    public SignUpUserFactory signUpUserFactory() {
        return new SignUpUserFactory();
    }
    
}
