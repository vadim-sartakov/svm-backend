package svm.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
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
        return (User) getAuthentication().getPrincipal();
    }
    
    public User getCurrentUserFromDb() {
        User currentUser = (User) getAuthentication().getPrincipal();
        return userRepository.findByUsername(currentUser.getUsername());
    }
    
}
