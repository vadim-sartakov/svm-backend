package svm.backend.data.config;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import svm.backend.data.annotation.PredefinedProcessor;
import svm.backend.data.migration.service.MigrationExecutor;

@Configuration
public class TestConfiguration {
    
    @MockBean private MigrationExecutor migrationExecutor;
    @MockBean private PredefinedProcessor predefinedProcessor;
            
}
