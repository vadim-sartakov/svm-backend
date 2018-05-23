package svm.backend.data.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import svm.backend.data.annotation.PredefinedProcessor;

@Configuration
@ConditionalOnProperty(name = "svm.backend.data.update", matchIfMissing = true)
public class PredefinedConfiguration implements InitializingBean {
    
    @Autowired private PredefinedProcessor predefinedProcessor;

    @Override
    public void afterPropertiesSet() throws Exception {
        predefinedProcessor.process();
    }
    
}
