package svm.backend.data.jpa.security.dao.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import svm.backend.data.annotation.Predefined;
import svm.backend.data.jpa.entity.Creatable;
import svm.backend.data.jpa.entity.Identifiable;
import svm.backend.data.jpa.entity.Updatable;
import svm.backend.security.model.BaseUser;

@Entity(name = "User")
@Table(name = "USERS")
@NamedEntityGraph(name = "UserOverview", attributeNodes = @NamedAttributeNode("authorities"))
public class User extends BaseUser implements UserDetails, Identifiable, Creatable, Updatable, Serializable {

    public static final @Predefined User SYSTEM = predefined(User.class, "system", true, new Authority(Role.SYSTEM));
    public static final @Predefined User ADMIN = predefined(User.class, "admin", false, new Authority(Role.ADMIN));
    
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    @Override
    public UUID getId() {
        return id;
    }

    @NotEmpty
    @Column(nullable = false, unique = true)
    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Column(nullable = false)
    @Override
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    @Column(nullable = false)
    @Override
    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    @Valid
    @ElementCollection(targetClass = Authority.class)
    @CollectionTable(name = "USERS_AUTHORITIES")
    @Override
    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Column(nullable = false)
    @Override
    public Boolean getDisabled() {
        return disabled;
    }
    
    @Transient
    @Override
    public boolean isAccountNonExpired() {
        return expiresAt == null || expiresAt.isAfter(ZonedDateTime.now());
    }

    @Transient
    @Override
    public boolean isAccountNonLocked() {
        return !disabled;
    }

    @Transient
    @Override
    public boolean isCredentialsNonExpired() {
        return !disabled;
    }

    @Transient
    @Override
    public boolean isEnabled() {
       return !disabled;
    }
    
}
