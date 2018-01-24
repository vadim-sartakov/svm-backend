package svm.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import svm.backend.security.JWTAuthenticationFilter;
import svm.backend.security.JWTCsrfTokenRepository;
import svm.backend.security.Pbkdf2PasswordEncoder;
import svm.backend.security.JWTAuthenticationService;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public abstract class WebSecurityBaseConfig extends WebSecurityConfigurerAdapter {
    
    private final CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
        
    @Bean
    public Pbkdf2PasswordEncoder encoder() {
        return new Pbkdf2PasswordEncoder();
    }
    
    @Bean
    public JWTAuthenticationService tokenAuthenticationService() {
        return new JWTAuthenticationService();
    }
    
    @Bean
    public JWTAuthenticationFilter authenticationFilter() {
        return new JWTAuthenticationFilter();
    }
        
    // For use of expression language
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
            
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
                .cors()
                .and()
                    .csrf()
                    .csrfTokenRepository(new JWTCsrfTokenRepository())
                    .ignoringAntMatchers("/api/*", "/api/forgot/**", "/api/sign-up/**")
                .and()
                .authorizeRequests()
                    .antMatchers("/api/home/**")
                        .authenticated()
                .and()
                    .logout().logoutUrl("/logout").deleteCookies(JWTAuthenticationService.COOKIE_NAME,
                            JWTCsrfTokenRepository.DEFAULT_CSRF_COOKIE_NAME
                    ).logoutSuccessUrl("/")
                .and()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(accessDeniedHandler)
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);  

    }
    
}
