package svm.backend.data.core.config;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import svm.backend.data.core.annotation.PredefinedProcessor;
import svm.backend.data.core.migration.model.MigrationRollback;
import svm.backend.data.core.migration.model.MigrationUpdate;
import svm.backend.data.core.migration.service.MigrationExecutor;
import svm.backend.data.core.migration.service.MigrationRepository;

@Configuration
@ConditionalOnMissingBean(DataAutoConfiguration.class)
@ComponentScan({ "svm.backend.data.core.security", "svm.backend.data.core.exception.handler" })
@PropertySource("classpath:properties/data/application.properties")
@EnableConfigurationProperties(DataProperties.class)
@Import(MessagesConfiguration.class)
public class DataAutoConfiguration {
    
    @Bean
    @ConditionalOnProperty(name = "svm.backend.data.update", matchIfMissing = true)
    @DependsOn("predefinedProcessorInitializer")
    public MigrationExecutor migrationExecutor(MigrationRepository migrationRepository,
                                               Optional<List<MigrationUpdate>> updates,
                                               Optional<List<MigrationRollback>> rollbacks) {
        return new MigrationExecutor(migrationRepository, updates, rollbacks);
    }
    
    @Bean
    @ConditionalOnProperty(name = "svm.backend.data.update", matchIfMissing = true)
    public PredefinedProcessorInitilizer predefinedProcessorInitializer() {
        return new PredefinedProcessorInitilizer();
    }
    
    public static class PredefinedProcessorInitilizer implements InitializingBean {

        @Autowired private PredefinedProcessor predefinedProcessor;
        
        @Override
        public void afterPropertiesSet() throws Exception {
             predefinedProcessor.process();
        }
        
    }
    
}
