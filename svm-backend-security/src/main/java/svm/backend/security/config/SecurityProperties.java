package svm.backend.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
@ConfigurationProperties("svm.backend.security")
public class SecurityProperties {
        
    @NestedConfigurationProperty
    private OAuth2Properties oauth2;
    
    @NestedConfigurationProperty
    private AuthorizationServerProperties authorizationServer;
    
    @Data
    public static class OAuth2Properties {
        private TokenStore tokenStore = TokenStore.JWT;
        public enum TokenStore {
            JWT,
            DAO
        }
    }
    
    @Data
    public static class AuthorizationServerProperties {
        private boolean enable = false;        
    }
    
}
