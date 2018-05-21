package svm.backend.data.jpa.security.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import svm.backend.data.jpa.security.service.OauthClientDetailsService;

@Configuration
@EntityScan("svm.backend.data.jpa.security.dao.entity")
@EnableJpaRepositories("svm.backend.data.jpa.security.dao")
@Import(MigrationsConfiguration.class)
public class SecurityJpaAutoConfiguration {
    @Bean
    public ClientDetailsService clientDetailsService() {
        return new OauthClientDetailsService();
    }    
}
