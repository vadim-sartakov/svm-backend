package svm.backend.security.config;

import java.util.Optional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessagesConfiguration implements InitializingBean {
    
    @Autowired private Optional<ReloadableResourceBundleMessageSource> messageSource;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        messageSource.ifPresent(source -> source.addBasenames(
                "classpath:locale/security/messages",
                "classpath:org/springframework/security/messages")
        );
    }
    
}
