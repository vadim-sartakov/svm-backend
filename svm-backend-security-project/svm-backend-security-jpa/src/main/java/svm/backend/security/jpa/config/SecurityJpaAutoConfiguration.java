package svm.backend.security.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import svm.backend.security.jpa.service.OauthClientDetailsService;

@Configuration
@EntityScan("svm.backend.security.jpa.dao.entity")
@EnableJpaRepositories("svm.backend.security.jpa.dao")
public class SecurityJpaAutoConfiguration {
    @Bean
    public ClientDetailsService clientDetailsService() {
        return new OauthClientDetailsService();
    }
}
