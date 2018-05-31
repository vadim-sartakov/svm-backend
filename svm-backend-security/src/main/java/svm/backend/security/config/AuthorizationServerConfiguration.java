package svm.backend.security.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

@Configuration
@ConditionalOnProperty("svm.backend.security.authorization-server.enable")
@Import(AuthorizationServerConfiguration.JwtConfiguration.class)
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired @Qualifier("daoClientDetailsService") private ClientDetailsService clientDetailsService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private TokenStore tokenStore;
    @Autowired private List<TokenEnhancer> tokenEnhancers;
    @Autowired private WebResponseExceptionTranslator translator;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain enhacerChain = new TokenEnhancerChain();
        enhacerChain.setTokenEnhancers(tokenEnhancers);
        endpoints
                .exceptionTranslator(translator)
                .tokenEnhancer(enhacerChain)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.passwordEncoder(passwordEncoder);
    }
    
    @Configuration
    @ConditionalOnProperty(name = "svm.backend.security.oauth2.token-store", havingValue = "JWT", matchIfMissing = true)
    public static class JwtConfiguration {

        @Autowired private SecurityProperties properties;
        
        @Bean
        public TokenStore tokenStore() {
            return new JwtTokenStore(accessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            
            SecurityProperties.AuthorizationServerProperties authServerProperties = properties.getAuthorizationServer();
            
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                    new ClassPathResource(authServerProperties.getKeyStore()),
                    authServerProperties.getKeyStorePassword().toCharArray());
            converter.setKeyPair(keyStoreKeyFactory.getKeyPair(authServerProperties.getKeyAlias()));
            
            return converter;
            
        }

    }
    
}
