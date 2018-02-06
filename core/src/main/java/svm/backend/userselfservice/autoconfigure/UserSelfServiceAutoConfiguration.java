package svm.backend.userselfservice.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.userselfservice.controller.RestoreController;
import svm.backend.userselfservice.controller.SignUpController;
import svm.backend.userselfservice.controller.TemporalPasswordController;
import svm.backend.userselfservice.service.SignUpEmailSender;
import svm.backend.userselfservice.service.SignUpUserFactory;

@Configuration
@ConditionalOnMissingBean(UserSelfServiceAutoConfiguration.class)
public class UserSelfServiceAutoConfiguration {
    
    @Bean
    public SignUpEmailSender signUpEmailSender() {
        return new SignUpEmailSender();
    }
    
    @Bean
    public SignUpController signUpController() {
        return new SignUpController();
    }
    
    @Bean
    public RestoreController restoreController() {
        return new RestoreController();
    }
    
    @Bean
    public TemporalPasswordController temporalPasswordController() {
        return new TemporalPasswordController();
    }
    
    @Bean
    public SignUpUserFactory signUpUserFactory() {
        return new SignUpUserFactory();
    }
    
}
