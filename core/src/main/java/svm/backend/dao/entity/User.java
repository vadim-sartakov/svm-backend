package svm.backend.dao.entity;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import svm.backend.dao.entity.contact.Contact;
import svm.backend.dao.entity.contact.Email;
import svm.backend.dao.entity.contact.PhoneNumber;
import svm.backend.dao.entity.listeners.OwnerListener;

@Data
@EqualsAndHashCode(of = "username", callSuper = true)

@Entity
@Table(name = "USERS")
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedEntityGraph(name = "user.overview", attributeNodes = {
    @NamedAttributeNode("roles"),
    @NamedAttributeNode("emails"),
    @NamedAttributeNode("phoneNumbers")
})
@EntityListeners(OwnerListener.class)
public class User extends UUIDEntity implements UserDetails {
        
    @NotNull
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private ZonedDateTime creationDate = ZonedDateTime.now();
    private ZonedDateTime expiresAt;
    private ZonedDateTime blockedTill;
    
    @Column(nullable = false)
    private Boolean disabled = false;
        
    @OneToMany(mappedBy = "user", targetEntity = Contact.class, cascade = CascadeType.ALL)
    private Set<Email> emails;
    
    @OneToMany(mappedBy = "user", targetEntity = Contact.class, cascade = CascadeType.ALL)
    private Set<PhoneNumber> phoneNumbers;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> roles;

    @NotNull
    @Column(nullable = false, length = 150)
    private String password;
        
    public boolean isInRole(String roleToFind) {
        for (UserRole currentRole : roles)
            if (currentRole.getRole().equals(roleToFind))
                return true;
        return false;
    }
    
    public void addRole(String role) {
        if (roles == null)
            roles = new HashSet<>();
        roles.add(UserRole.of(role));
    }
    
    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getRole()))
        );
        return authorities;
    }
    
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        roles = new HashSet<>();
        authorities.forEach(authority -> roles.add(UserRole.of(authority.getAuthority())));
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return expiresAt == null || expiresAt.isBefore(ZonedDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !disabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }
            
}
