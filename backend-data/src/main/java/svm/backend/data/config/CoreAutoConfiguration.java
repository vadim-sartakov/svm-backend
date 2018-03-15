package svm.backend.data.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:data.properties")
public class CoreAutoConfiguration implements InitializingBean {
    
    @Autowired private ReloadableResourceBundleMessageSource messageSource;

    @Override
    public void afterPropertiesSet() throws Exception {
        messageSource.addBasenames("classpath:locale/messages", "classpath:locale/data_messages");
    }

}
