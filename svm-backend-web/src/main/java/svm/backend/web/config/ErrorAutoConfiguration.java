package svm.backend.web.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import svm.backend.web.exception.CustomErrorAttributes;
import svm.backend.web.exception.handler.ByStatusExceptionHandler;
import svm.backend.web.exception.handler.ByTypeExceptionHandler;

@Configuration
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@ConditionalOnMissingBean(CustomErrorAttributes.class)
@Import(CustomErrorAttributes.class)
public class ErrorAutoConfiguration {
    
    /*@Bean
    @ConditionalOnMissingBean(CustomErrorAttributes.class)
    public CustomErrorAttributes customErrorAttributes(Optional<List<ByTypeExceptionHandler<?>>> byClassHandlers,
            List<ByStatusExceptionHandler> byStatusHandlers) {
        return new CustomErrorAttributes(byClassHandlers.orElse(new ArrayList<>()), byStatusHandlers);
    }*/
    
}
