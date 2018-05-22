package svm.backend.data.jpa.security.dao.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import svm.backend.data.jpa.entity.Identifiable;
import svm.backend.data.jpa.security.dao.entity.converter.Oauth2ClientPropertiesConverter;
import svm.backend.security.model.BaseOauth2Client;

@Entity(name = "Oauth2Client")
@Table(name = "OAUTH2_CLIENTS")
@Access(AccessType.PROPERTY)
public class Oauth2Client extends BaseOauth2Client implements Serializable, Identifiable, ClientDetails {

    public static final BaseOauth2Client DEFAULT = predefined(Oauth2Client.class, "default", new JpaGrantedAuthority(Role.ADMIN));
    
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    @Override
    public UUID getId() {
        return id;
    }

    @Column(nullable = false, unique = true)
    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }
    
    @Column(nullable = false)
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Column(nullable = false)
    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Valid
    @NotEmpty
    @ElementCollection(targetClass = JpaGrantedAuthority.class)
    @CollectionTable(name = "OAUTH2_CLIENTS_AUTHORITIES")
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Convert(converter = Oauth2ClientPropertiesConverter.class)
    @Column(nullable = false)
    @Override
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
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @Transient
    @Override
    public boolean isScoped() {
        return properties.getScope() != null && !properties.getScope().isEmpty();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (properties.getAutoApproveScopes() == null) {
            return false;
        }
        for (String auto : properties.getAutoApproveScopes()) {
            if (auto.equals("true") || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    @Transient
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
    
}
