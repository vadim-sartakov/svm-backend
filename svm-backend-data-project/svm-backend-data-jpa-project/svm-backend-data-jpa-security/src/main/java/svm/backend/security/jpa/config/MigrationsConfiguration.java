package svm.backend.security.jpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import svm.backend.security.jpa.dao.migration.Security_1_0_0;

@Configuration
public class MigrationsConfiguration {
    @Bean
    public Security_1_0_0 security_1_0_0() {
        return new Security_1_0_0();
    }
}