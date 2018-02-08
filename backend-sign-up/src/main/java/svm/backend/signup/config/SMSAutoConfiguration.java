package svm.backend.signup.config;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import svm.backend.signup.service.PhonePasswordGenerator;
import svm.backend.signup.service.SimplePhonePasswordGenerator;
import svm.backend.sms.SmsMessage;

@Configuration
@ConditionalOnMissingBean(SMSAutoConfiguration.class)
public class SMSAutoConfiguration {
    
    @Bean
    public PhonePasswordGenerator phonePasswordGenerator() {
        return new SimplePhonePasswordGenerator();
    }
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SmsMessage smsMessageTemplate() {
        return new SmsMessage("", "%s");
    }
    
}
