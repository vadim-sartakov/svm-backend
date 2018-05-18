package svm.backend.security.jpa.dao.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

@Entity
@Table(name = "OAUTH_CLIENTS")
public class OauthClient extends BaseClientDetails implements Serializable {
        
    @Id
    @Override
    public String getClientId() {
        return super.getClientId();
    }
    
    @Override
    public String getClientSecret() {
        return super.getClientSecret();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return super.getAccessTokenValiditySeconds();
    }
    
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return super.getRefreshTokenValiditySeconds();
    }

    @ElementCollection(targetClass = JpaGrantedAuthority.class)
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @ElementCollection
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return super.getRegisteredRedirectUri();
    }

    @ElementCollection
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return super.getAuthorizedGrantTypes();
    }

    @ElementCollection
    @Override
    public Set<String> getResourceIds() {
        return super.getResourceIds();
    }

    @ElementCollection
    @Override
    public Set<String> getScope() {
        return super.getScope();
    }

    @ElementCollection
    @Override
    public Set<String> getAutoApproveScopes() {
        return super.getAutoApproveScopes();
    }

}
