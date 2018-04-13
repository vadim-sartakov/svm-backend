package svm.backend.data.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.data.jpa.migration.JpaMigrationRepository;
import svm.backend.data.migration.service.MigrationRepository;

@Configuration
public class ServiceAutoConfiguration {
    @Bean
    public MigrationRepository migrationRepository() {
        return new JpaMigrationRepository();
    }
}
