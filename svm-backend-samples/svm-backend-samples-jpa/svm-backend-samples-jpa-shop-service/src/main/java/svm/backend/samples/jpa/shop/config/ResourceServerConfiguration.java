package svm.backend.samples.jpa.shop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class ResourceServerConfiguration extends svm.backend.security.config.ResourceServerConfiguration {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/home/protected")
                .authenticated()
                .antMatchers("/home/public")
                .permitAll()
                .antMatchers("/api/orders")
                .hasAnyAuthority("ROLE_MANAGER");
    }
    
}
