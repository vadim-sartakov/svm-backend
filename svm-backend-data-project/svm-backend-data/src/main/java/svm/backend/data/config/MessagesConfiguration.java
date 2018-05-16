package svm.backend.data.config;

import java.util.Optional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@ConditionalOnBean(MessageSource.class)
public class MessagesConfiguration implements InitializingBean {
    
    @Autowired private Optional<ReloadableResourceBundleMessageSource> messageSource;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        messageSource.ifPresent(ms -> ms.addBasenames("classpath:locale/data/messages"));
    }
    
}
