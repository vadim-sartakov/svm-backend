package svm.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import svm.backend.security.dao.entity.User;
import svm.backend.security.dao.repository.UserRepository;

public class AuthenticationInfo {
    
    @Autowired private UserRepository userRepository;
    
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
        
    public User getCurrentUser() {
        
        Authentication authentication = getAuthentication();
        User user;
        if (authentication instanceof AnonymousAuthenticationToken)
            user = new User();
        else
            user = (User) authentication.getPrincipal();
        
        return user;
        
    }
    
    public boolean isUserInRole(String roleToFind) {
        Authentication authentication = getAuthentication();
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals(roleToFind));
    }
    
    public User getCurrentUserFromDb() {
        User currentUser = (User) getAuthentication().getPrincipal();
        return userRepository.findByUsername(currentUser.getUsername());
    }
    
}
