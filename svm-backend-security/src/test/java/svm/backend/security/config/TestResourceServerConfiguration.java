package svm.backend.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class TestResourceServerConfiguration extends ResourceServerConfiguration{
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/controller/secured")
                .authenticated()
                .antMatchers("/api/controller/private")
                .hasAuthority("ROLE_ADMIN")
                .anyRequest()
                .permitAll();
    }
}
