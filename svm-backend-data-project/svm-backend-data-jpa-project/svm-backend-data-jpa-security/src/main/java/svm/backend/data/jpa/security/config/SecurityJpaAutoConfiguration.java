package svm.backend.data.jpa.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import svm.backend.data.jpa.security.service.JpaUserDetailsService;
import svm.backend.data.jpa.security.service.OauthClientDetailsService;

@Configuration
public class SecurityJpaAutoConfiguration {
    
    @Bean
    public ClientDetailsService daoClientDetailsService() {
        return new OauthClientDetailsService();
    }
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        return new JpaUserDetailsService();
    }
    
}
