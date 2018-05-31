package svm.backend.security.config;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@ConditionalOnMissingBean(ResourceServerConfiguration.class)
@EnableResourceServer
@Import({ ResourceServerConfiguration.JwtConfiguration.class })
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    
    @Autowired private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired private AccessDeniedHandler accessDeniedHandler;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint);
    }
    
    @Configuration
    @ConditionalOnProperty(name = "svm.backend.security.oauth2.token-store", havingValue = "JWT", matchIfMissing = true)
    public static class JwtConfiguration {
        
        @Autowired private JwtAccessTokenConverter tokenConverter;
                
        @Bean
        public AdditionalClaimsTokenConverter additionalClaimsTokenConverter() {
            AdditionalClaimsTokenConverter converter = new AdditionalClaimsTokenConverter();
            tokenConverter.setAccessTokenConverter(converter);
            return new AdditionalClaimsTokenConverter();
        }
        
        public static class AdditionalClaimsTokenConverter extends DefaultAccessTokenConverter {
            @Override
            public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
                OAuth2Authentication authentication = super.extractAuthentication(claims);
                authentication.setDetails(claims);
                return authentication;
            }
        }
        
    }
    
}
