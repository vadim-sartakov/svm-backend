package svm.backend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import svm.backend.security.AuthenticationInfo;
import svm.backend.security.CustomUserDetailsService;
import svm.backend.security.JWTCsrfTokenRepository;
import svm.backend.security.JWTService;
import svm.backend.security.filter.JWTAuthenticationFilter;
import svm.backend.security.filter.JWTAuthorizationFilter;

//@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public abstract class WebSecurityBaseConfig extends WebSecurityConfigurerAdapter {
                
    @Bean
    public CustomAccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }
    
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
    
    @Bean
    public JWTService tokenAuthenticationService() {
        return new JWTService();
    }
    
    @Bean
    public JWTAuthenticationFilter authenticationFilter() {
        try {
            return new JWTAuthenticationFilter(authenticationManager());
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
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
    
    @Bean
    public AuthenticationInfo authenticationInfo() {
        return new AuthenticationInfo();
    }
            
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
                .cors()
                .and()
                    .csrf()
                    .csrfTokenRepository(csrfTokenRepository())
                    .ignoringAntMatchers(
                            "/*",
                            "/{basePath:^(?!api).*$}/**",
                            "/api/{basePath:^(?!home).*$}/**")
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
                    .accessDeniedHandler(accessDeniedHandler())
                    .authenticationEntryPoint(accessDeniedHandler())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilter(authenticationFilter())
                    .addFilter(authorizationFilter());  

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(bCryptPasswordEncoder());
    }
    
    // TODO: implements multiple authentication providers: Active Directory (Kerberos),
    // Oauth2 (google, facebook, etc.)
    
}
