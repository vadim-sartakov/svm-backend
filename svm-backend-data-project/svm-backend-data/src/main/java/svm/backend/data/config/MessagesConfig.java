package svm.backend.data.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessagesConfig implements InitializingBean {
    
    @Autowired private ReloadableResourceBundleMessageSource messageSource;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        messageSource.addBasenames("classpath:locale/data/messages");
    }
    
}
