package svm.backend.security.oauth2.client.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.context.annotation.RequestScope;
import svm.backend.security.oauth2.client.OAuth2StatelessClientAuthenticationFilter;
import svm.backend.security.oauth2.client.OAuth2StatelessClientContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableOAuth2Client
public class OAuth2StatelessClientConfiguration {
    
    @Autowired private AccessTokenRequest accessTokenRequest;

    @Autowired private HttpServletRequest request;
    @Autowired private HttpServletResponse response;
    @Autowired private ServletContext servletContext;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private TokenStore tokenStore;

    @Autowired private OAuth2RestTemplate restTemplate;
    @Autowired private ResourceServerTokenServices tokenServices;
    
    @Bean
    @RequestScope(proxyMode = ScopedProxyMode.INTERFACES)
    @Primary
    public OAuth2ClientContext oauth2StatelessClientContext() {
        return OAuth2StatelessClientContext.builder()
                .accessTokenRequest(accessTokenRequest)
                .request(request)
                .response(response)
                .servletContext(servletContext)
                .objectMapper(objectMapper)
                .tokenStore(tokenStore)
                .build();
    }
    
    @Bean
    public OAuth2StatelessClientAuthenticationFilter authenticationFilter() {
        return new OAuth2StatelessClientAuthenticationFilter(tokenServices, restTemplate, oauth2StatelessClientContext());
    }
    
}
