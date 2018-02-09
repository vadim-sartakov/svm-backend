package svm.backend.signup.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import svm.backend.signup.controller.EmailPasswordController;
import svm.backend.signup.controller.PhonePasswordController;
import svm.backend.signup.controller.RestoreController;
import svm.backend.signup.controller.SignUpController;
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
    public PhonePasswordController phonePasswordController() {
        return new PhonePasswordController();
    }
    
    @Bean
    public EmailPasswordController emailPasswordController() {
        return new EmailPasswordController();
    }
    
    @Bean
    public RestoreController restoreController() {
        return new RestoreController();
    }
    
    @Bean
    public SignUpUserFactory signUpUserFactory() {
        return new SignUpUserFactory();
    }
    
}
