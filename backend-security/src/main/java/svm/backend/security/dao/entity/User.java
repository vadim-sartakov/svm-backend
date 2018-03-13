package svm.backend.security.dao.entity;

import svm.backend.data.entity.UUIDEntity;
import svm.backend.data.entity.Creatable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import svm.backend.data.entity.validator.UniqueValues;
import svm.backend.data.entity.validator.UniqueValues.UniqueValue;
import svm.backend.security.json.View;

@Getter
@Setter
@ToString

@UniqueValues(@UniqueValue(fields = "username", ignoreCaseExpr = "${svm.backend.security.findUserIgnoreCase}"))
@Entity(name = "BaseUser")
@Table(name = "USERS")
@NamedEntityGraph(name = "user.overview", attributeNodes = @NamedAttributeNode("roles"))
public class User extends UUIDEntity implements UserDetails, Creatable {
        
    public final static User ADMIN = UUIDEntity.of("ADMIN", User.class);
       
    @NotNull
    @Column(nullable = false, unique = true)
    protected String username;
    
    @JsonView(View.Admin.class)
    @Column(nullable = false)
    protected ZonedDateTime createdAt;
    
    @JsonView(View.Admin.class)
    protected ZonedDateTime expiresAt;
    
    @JsonView(View.Admin.class)
    protected ZonedDateTime blockedTill;
    
    @JsonView(View.Admin.class)
    @Column(nullable = false)
    protected Boolean disabled = false;
            
    @JsonView(View.Admin.class)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    protected Set<UserRole> roles = new HashSet<>();
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Column(nullable = false, length = 150)
    protected String password;
        
    public void addRole(String role) {
        roles.add(UserRole.of(this, role));
    }
    
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getRole()))
        );
        return authorities;
    }
    
    @JsonIgnore
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        authorities.forEach(authority -> roles.add(UserRole.of(authority.getAuthority())));
    }
    
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return expiresAt == null || expiresAt.isBefore(ZonedDateTime.now());
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !disabled;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return !disabled;
    }
                
}
