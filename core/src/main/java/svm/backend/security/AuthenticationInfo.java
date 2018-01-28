package svm.backend.security;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import svm.backend.dao.entity.UUIDEntity;
import svm.backend.dao.entity.User;

public class AuthenticationInfo {
    
    @Autowired private UserDetailsService userDetailsService;
    
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
    
    public boolean isUserInRole(String role) {
        return getAuthentication()
                .getAuthorities().contains(new SimpleGrantedAuthority(role));
    }
    
    public User getCurrentUser() {
        return UUIDEntity.newInstance(
                UUID.fromString(getAuthentication().getName()), User.class
        );
    }
    
    public User getCurrentUserFromDb() {
        return (User) userDetailsService.loadUserByUsername(getAuthentication().getName());
    }
    
}
