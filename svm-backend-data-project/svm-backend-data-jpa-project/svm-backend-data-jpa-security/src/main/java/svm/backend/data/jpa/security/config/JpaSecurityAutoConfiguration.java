package svm.backend.data.jpa.security.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import svm.backend.data.jpa.security.service.OauthClientDetailsService;

@Configuration
@EntityScan("svm.backend.data.jpa.security.dao.entity")
@EnableJpaRepositories("svm.backend.data.jpa.security.dao")
public class JpaSecurityAutoConfiguration {
    @Bean
    public ClientDetailsService daoClientDetailsService() {
        return new OauthClientDetailsService();
    }    
}
