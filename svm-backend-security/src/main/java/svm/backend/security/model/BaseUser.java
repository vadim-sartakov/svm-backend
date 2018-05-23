package svm.backend.security.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public abstract class BaseUser implements UserDetails, Serializable {
    
    protected UUID id;
    protected String username;
    protected String password;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;
    protected ZonedDateTime expiresAt;
    protected List<GrantedAuthority> authorities;
    protected Boolean disabled = false;

    public static <T extends BaseUser> T predefined(Class<T> type, String username, Boolean disabled, BaseGrantedAuthority... authority) {
        T user = BeanUtils.instantiate(type);
        user.id = UUID.nameUUIDFromBytes(username.getBytes(StandardCharsets.UTF_8));
        ZonedDateTime now = ZonedDateTime.now();
        user.createdAt = now;
        user.updatedAt = now;
        user.username = username;
        user.authorities = Arrays.asList(authority);
        user.disabled = disabled;
        return user;
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
