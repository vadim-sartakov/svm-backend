package svm.backend.data.jpa.security.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("svm.backend.data.jpa.security.dao.entity")
@EnableJpaRepositories("svm.backend.data.jpa.security.dao")
public class SecurityEntityAutoConfiguration {
    
}
