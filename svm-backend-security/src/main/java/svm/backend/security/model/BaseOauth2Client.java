package svm.backend.security.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

@Data
public abstract class BaseOauth2Client implements Serializable, ClientDetails {
            
    protected UUID id;
    protected String clientId;
    protected String clientSecret;
    protected Integer accessTokenValiditySeconds;
    protected Integer refreshTokenValiditySeconds;
    protected Collection<GrantedAuthority> authorities;
    protected Properties properties;
    
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return properties.getRegisteredRedirectUri();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return properties.getAuthorizedGrantTypes();
    }

    @Override
    public Set<String> getResourceIds() {
        return properties.getResourceIds();
    }

    @Override
    public Set<String> getScope() {
        return properties.getScope();
    }

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public boolean isScoped() {
        return properties.scope != null && !properties.scope.isEmpty();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (properties.autoApproveScopes == null) {
            return false;
        }
        for (String auto : properties.autoApproveScopes) {
            if (auto.equals("true") || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }

    public static <T extends BaseOauth2Client> T predefined(Class<T> type, String id, BaseGrantedAuthority... authority) {
        T client = BeanUtils.instantiate(type);
        client.setId(UUID.nameUUIDFromBytes(id.getBytes(StandardCharsets.UTF_8)));
        client.setClientId(id);
        client.setAuthorities(Arrays.asList(authority));
        client.setProperties(Properties.builder()
                .authorizedGrantType("client_credentials")
                .authorizedGrantType("password")
                .authorizedGrantType("refresh_token")
                .build());
        return client;
    }

    @Data
    @Builder
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Properties implements Serializable {
        @Singular("registeredRedirectUri")
        private Set<String> registeredRedirectUri;
        @Singular
        private Set<String> authorizedGrantTypes;
        @Singular
        private Set<String> resourceIds;
        @Singular("scope")
        private Set<String> scope;
        @Singular
        private Set<String> autoApproveScopes;
    }
    
}
