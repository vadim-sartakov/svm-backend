package svm.backend.datachange.config;

import svm.backend.datachange.service.DataChangeExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataChangeAutoConfigration {
        
    @Bean
    public DataChangeExecutor dataChangeExecutor() {
        return new DataChangeExecutor();
    }
            
}
