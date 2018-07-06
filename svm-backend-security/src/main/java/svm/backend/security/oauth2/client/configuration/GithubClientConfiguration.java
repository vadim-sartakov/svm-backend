package svm.backend.security.oauth2.client.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import svm.backend.security.oauth2.client.OAuth2StatelessClientContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class GithubClientConfiguration {
 
    @Autowired
    @Qualifier("accessTokenRequest")
    private AccessTokenRequest accessTokenRequest;

    @Autowired private HttpServletRequest request;
    @Autowired private HttpServletResponse response;
    @Autowired private ServletContext servletContext;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private TokenStore tokenStore;

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2ClientContext githubClientContext() {
        return OAuth2StatelessClientContext.builder()
                .accessTokenRequest(accessTokenRequest)
                .request(request)
                .response(response)
                .servletContext(servletContext)
                .objectMapper(objectMapper)
                .tokenStore(tokenStore)
                .prefix("github")
                .build();
    }
    
    @Bean
    @ConfigurationProperties("github.client")
    public AuthorizationCodeResourceDetails githubResourceDetails() {
        return new AuthorizationCodeResourceDetails();
    }
    
    @Bean
    @ConfigurationProperties("github.resource")
    public ResourceServerProperties githubResourceServerProperties() {
        return new ResourceServerProperties();
    }
    
    @Bean
    public OAuth2RestTemplate githubRestTemplate() {
        return new OAuth2RestTemplate(githubResourceDetails(), githubClientContext());
    }
    
    @Bean
    public ResourceServerTokenServices githubTokenServices() {
        ResourceServerProperties resourceServerProperties = githubResourceServerProperties();
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceServerProperties.getClientId());
        tokenServices.setRestTemplate(githubRestTemplate());
        return tokenServices;
    }

    @Bean
    public OAuth2ClientAuthenticationProcessingFilter githubClientAuthenticationFilter() {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
        filter.setRestTemplate(githubRestTemplate());
        filter.setTokenServices(githubTokenServices());
        return filter;
    }

}
