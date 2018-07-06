package svm.backend.security.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
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
    protected Set<String> registeredRedirectUri;
    protected Set<String> authorizedGrantTypes;
    protected Set<String> resourceIds;
    protected Set<String> scope;
    protected Set<String> autoApproveScopes;
    protected Map<String, Object> additionalInformation;

    @Override
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Override
    public boolean isScoped() {
        return scope != null && !scope.isEmpty();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (autoApproveScopes == null) {
            return false;
        }
        for (String auto : autoApproveScopes) {
            if (auto.equals("true") || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends BaseOauth2Client> T predefined(Class<T> type, String id, BaseGrantedAuthority... authority) {
        T client = BeanUtils.instantiateClass(type);
        client.setId(UUID.nameUUIDFromBytes(id.getBytes(StandardCharsets.UTF_8)));
        client.setClientId(id);
        client.setAuthorities(Arrays.asList(authority));
        client.setAuthorizedGrantTypes(new LinkedHashSet<>(Arrays.asList("client_credentials", "password", "refresh_token")));
        client.setScope(new LinkedHashSet<>(Arrays.asList("default")));
        return client;
    }
    
}
