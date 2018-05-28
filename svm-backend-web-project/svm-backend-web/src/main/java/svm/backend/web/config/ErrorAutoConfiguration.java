package svm.backend.web.config;

import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.web.core.exception.ByStatusExceptionHandler;
import svm.backend.web.core.exception.ByTypeExceptionHandler;
import svm.backend.web.exception.CustomErrorAttributes;

@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
public class ErrorAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(CustomErrorAttributes.class)
    public CustomErrorAttributes customErrorAttributes(List<ByTypeExceptionHandler<?>> byClassHandlers,
            List<ByStatusExceptionHandler> byStatusHandlers) {
        return new CustomErrorAttributes(byClassHandlers, byStatusHandlers);
    }
    
}
