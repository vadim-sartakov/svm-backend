package svm.backend.security.jpa.dao.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import svm.backend.security.jpa.dao.entity.converter.Oauth2ClientPropertiesConverter;

@Entity
@Table(name = "OAUTH2_CLIENTS")
public class Oauth2Client extends BaseClientDetails implements Serializable {
        
    private @Setter Properties properties;
    
    @Id
    @Override
    public String getClientId() {
        return super.getClientId();
    }
    
    @NotNull
    @Column(nullable = false)
    @Override
    public String getClientSecret() {
        return super.getClientSecret();
    }

    @NotNull
    @Column(nullable = false)
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return super.getAccessTokenValiditySeconds();
    }
    
    @NotNull
    @Column(nullable = false)
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return super.getRefreshTokenValiditySeconds();
    }

    @Valid
    @NotEmpty
    @ElementCollection(targetClass = JpaGrantedAuthority.class)
    @CollectionTable(name = "OAUTH2_CLIENTS_AUTHORITIES")
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
    }

    @Convert(converter = Oauth2ClientPropertiesConverter.class)
    public Properties getProperties() {
        return properties;
    }

    @Transient
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return properties.getRegisteredRedirectUri();
    }

    @Transient
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return properties.getAuthorizedGrantTypes();
    }

    @Transient
    @Override
    public Set<String> getResourceIds() {
        return properties.getResourceIds();
    }

    @Transient
    @Override
    public Set<String> getScope() {
        return properties.getScope();
    }

    @Transient
    @Override
    public Set<String> getAutoApproveScopes() {
        return properties.getAutoApproveScopes();
    }

    @Data
    public static class Properties implements Serializable {
        private Set<String> registeredRedirectUri;
        private Set<String> authorizedGrantTypes;
        private Set<String> resourceIds;
        private Set<String> scope;
        private Set<String> autoApproveScopes;
    }
    
}
