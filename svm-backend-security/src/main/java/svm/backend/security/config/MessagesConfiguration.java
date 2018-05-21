package svm.backend.security.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ConditionalOnBean(ReloadableResourceBundleMessageSource.class)
public class MessagesConfiguration implements InitializingBean {
    
    @Autowired private ReloadableResourceBundleMessageSource messageSource;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        messageSource.addBasenames(
                "classpath:locale/security/messages",
                "classpath:org/springframework/security/messages");
    }
    
}
