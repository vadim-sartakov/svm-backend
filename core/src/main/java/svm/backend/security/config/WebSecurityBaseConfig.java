package svm.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import svm.backend.security.JWTCsrfTokenRepository;
import svm.backend.security.Pbkdf2PasswordEncoder;
import svm.backend.security.JWTService;
import svm.backend.security.filter.JWTAuthenticationFilter;
import svm.backend.security.filter.JWTAuthorizationFilter;

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
    public JWTService tokenAuthenticationService() {
        return new JWTService();
    }
    
    @Bean
    public JWTAuthenticationFilter authenticationFilter() {
        return new JWTAuthenticationFilter();
    }
    
    @Bean
    public JWTAuthorizationFilter authorizationFilter() {
        try {
            return new JWTAuthorizationFilter(authenticationManager());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
        
    // For use of expression language
    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }
    
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new JWTCsrfTokenRepository();
    }
            
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
                .cors()
                .and()
                    .csrf()
                    .csrfTokenRepository(csrfTokenRepository())
                    .ignoringAntMatchers("/api/{basePath:^(?!home).*$}/**")
                .and()
                .authorizeRequests()
                    .antMatchers("/api/home/**")
                        .authenticated()
                .and()
                    .logout().logoutUrl("/logout").deleteCookies(JWTService.COOKIE_NAME,
                            JWTCsrfTokenRepository.DEFAULT_CSRF_COOKIE_NAME
                    ).logoutSuccessUrl("/")
                .and()
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler)
                    .authenticationEntryPoint(accessDeniedHandler)
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilter(authenticationFilter())
                    .addFilter(authorizationFilter());  

    }
    
}
