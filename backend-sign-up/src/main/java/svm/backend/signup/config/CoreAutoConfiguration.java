package svm.backend.signup.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import svm.backend.signup.controller.SignUpController;
import svm.backend.signup.controller.confirm.EmailConfirmController;
import svm.backend.signup.controller.confirm.PhoneConfirmController;
import svm.backend.signup.controller.restore.EmailRestoreController;
import svm.backend.signup.controller.restore.PhoneRestoreController;
import svm.backend.signup.service.EmailPasswordSender;
import svm.backend.signup.service.PhonePasswordSender;
import svm.backend.signup.service.SignUpUserFactory;
import svm.backend.signup.service.UserAccountUserDetailsService;

@Configuration
@PropertySource("classpath:sign-up.properties")
public class CoreAutoConfiguration implements InitializingBean {
    
    @Autowired private ReloadableResourceBundleMessageSource messageSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserAccountUserDetailsService();
    }
    
    @Bean
    public SignUpController signUpController() {
        return new SignUpController();
    }
        
    @Bean
    public EmailConfirmController emailSignUpController() {
        return new EmailConfirmController();
    }
    
    @Bean
    public PhoneConfirmController phoneActivateController() {
        return new PhoneConfirmController();
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
    public EmailPasswordSender emailPasswordSender() {
        return new EmailPasswordSender();
    }
    
    @Bean
    public PhonePasswordSender phonePasswordSender() {
        return new PhonePasswordSender();
    }
    
    @Bean
    public SignUpUserFactory signUpUserFactory() {
        return new SignUpUserFactory();
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        messageSource.addBasenames("classpath:locale/sign-up_messages");
    }
    
}
