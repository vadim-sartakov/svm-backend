package svm.backend.data.jpa.security.dao.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import svm.backend.data.core.annotation.Predefined;
import svm.backend.data.jpa.core.entity.Identifiable;
import svm.backend.data.jpa.core.converter.MapJsonStringConverter;
import svm.backend.data.jpa.core.converter.StringSetConverter;
import svm.backend.security.model.BaseOauth2Client;

@Entity(name = "Oauth2Client")
@Table(name = "OAUTH2_CLIENTS")
@NamedEntityGraph(name = "Oauth2ClientOverview", attributeNodes = @NamedAttributeNode("authorities"))
public class Oauth2Client extends BaseOauth2Client implements Serializable, Identifiable, ClientDetails {

    public static final @Predefined Oauth2Client DEFAULT = predefined(Oauth2Client.class, "default", new Authority(Role.ADMIN));
    
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
    
    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    @Valid
    @NotEmpty
    @ElementCollection(targetClass = Authority.class)
    @CollectionTable(name = "OAUTH2_CLIENTS_AUTHORITIES")
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Convert(converter = StringSetConverter.class)
    @Override
    public Set<String> getAutoApproveScopes() {
        return super.getAutoApproveScopes();
    }

    @Convert(converter = StringSetConverter.class)
    @Override
    public Set<String> getScope() {
        return super.getScope();
    }

    @Convert(converter = StringSetConverter.class)
    @Override
    public Set<String> getResourceIds() {
        return super.getResourceIds();
    }

    @Convert(converter = StringSetConverter.class)
    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return super.getAuthorizedGrantTypes();
    }

    @Convert(converter = StringSetConverter.class)
    @Override
    public Set<String> getRegisteredRedirectUri() {
        return super.getRegisteredRedirectUri();
    }

    @Transient
    @Override
    public boolean isSecretRequired() {
        return super.isSecretRequired();
    }

    @Transient
    @Override
    public boolean isScoped() {
        return super.isScoped();
    }

    @Convert(converter = MapJsonStringConverter.class)
    @Override
    public Map<String, Object> getAdditionalInformation() {
        return super.getAdditionalInformation();
    }
    
}
