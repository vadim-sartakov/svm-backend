package svm.backend.security.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
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
import svm.backend.security.JWTAuthenticationFilter;
import svm.backend.security.TokenAuthenticationService;

/**
 * This config has order 99 because WebSecurityConfigurerAdapter config
 * defined by client always would have order 100.
 * @author Вадим
 */
@Configuration
@Order(99)
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityAutoConfig/* extends WebSecurityConfigurerAdapter*/ {

    /*private final CustomAccessDeniedHandler accessDeniedHandler;
    
    public WebSecurityAutoConfig() {
        accessDeniedHandler = new CustomAccessDeniedHandler();
    }
    
    @Bean
    public Pbkdf2PasswordEncoder encoder() {
        return new Pbkdf2PasswordEncoder();
    }
    
    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return new TokenAuthenticationService();
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
    
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new JWTCsrfTokenRepository();
    }
        
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
                .cors()
                .and()
                    .csrf().disable()
                    //.csrfTokenRepository(csrfTokenRepository())
                    //.ignoringAntMatchers("/static**", "/api/sign-in")
                //.and()
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/api/sign-in", "/api/logout")
                        .authenticated()
                    .antMatchers("/**").permitAll()
                .and()
                    .logout().logoutUrl("/api/logout").deleteCookies("token").logoutSuccessUrl("/api/sign-in")
                .and()
                    .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(accessDeniedHandler)
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);  

    }*/
     
}
