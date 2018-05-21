package svm.backend.security.jpa.dao.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
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
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import svm.backend.data.jpa.entity.Identifiable;
import svm.backend.security.jpa.dao.entity.converter.Oauth2ClientPropertiesConverter;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "OAUTH2_CLIENTS")
@Access(AccessType.FIELD)
public class Oauth2Client implements Serializable, Identifiable, ClientDetails {
        
    public static final String DEFAULT = "default";
    public static final Map<String, Oauth2Client> PREDEFINED;
    
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    private UUID id;
    
    @Column(nullable = false, unique = true)
    private String clientId;
    private String clientSecret;
    
    @Column(nullable = false)
    private Integer accessTokenValiditySeconds;
    
    @Column(nullable = false)
    private Integer refreshTokenValiditySeconds;
    
    @Singular
    @Valid
    @NotEmpty
    @ElementCollection(targetClass = JpaGrantedAuthority.class)
    @CollectionTable(name = "OAUTH2_CLIENTS_AUTHORITIES")
    private Collection<GrantedAuthority> authorities;
    
    @Convert(converter = Oauth2ClientPropertiesConverter.class)
    @Column(nullable = false)
    private Properties properties;
    
    static {
        PREDEFINED = new HashMap<>();
        PREDEFINED.put(DEFAULT,
                Oauth2Client.builder()
                        .clientId(DEFAULT)
                        .authority(
                                JpaGrantedAuthority.builder()
                                        .role(Role.PREDEFINED.get(Role.ADMIN))
                                        .build()
                        )
                        .accessTokenValiditySeconds(60 * 60)
                        .refreshTokenValiditySeconds(60 * 60 * 24)
                        .properties(Properties.builder()
                                .scope("read")
                                .scope("write")
                                .build()
                        ).build()
        );
    }

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
