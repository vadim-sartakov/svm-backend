package svm.backend.data.jpa.security.dao.entity;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
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
import org.springframework.security.core.userdetails.UserDetails;
import svm.backend.data.jpa.entity.Creatable;
import svm.backend.data.jpa.entity.Identifiable;
import svm.backend.data.jpa.entity.Updatable;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User implements UserDetails, Identifiable, Creatable, Updatable, Serializable {
    
    public static final User SYSTEM = createUser("system", Role.SYSTEM, true);
    public static final User ADMIN = createUser("admin", Role.ADMIN, false);

    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    private UUID id;
    
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    
    @Column(nullable = false)
    private ZonedDateTime createdAt;
    
    @Column(nullable = false)
    private ZonedDateTime updatedAt;
    private ZonedDateTime expiresAt;
    
    @Valid
    @Singular
    @ElementCollection
    @CollectionTable(name = "USERS_AUTHORITIES")
    private List<JpaGrantedAuthority> authorities;
    
    @Builder.Default
    @Column(nullable = false)
    private Boolean disabled = false;

    private static User createUser(String username, Role role, Boolean disabled) {
        return User.builder()
                .id(UUID.nameUUIDFromBytes(username.getBytes(StandardCharsets.UTF_8)))
                .username(username)
                .authority(JpaGrantedAuthority.builder().role(role).build())
                .disabled(disabled)
                .build();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return expiresAt == null || expiresAt.isAfter(ZonedDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !disabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !disabled;
    }

    @Override
    public boolean isEnabled() {
       return !disabled;
    }
    
}
