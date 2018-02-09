package svm.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import svm.backend.security.dao.entity.User;
import svm.backend.security.dao.repository.UserRepository;

public class UsernameUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    
    @Value("${svm.backend.security.findUserIgnoreCase:true}")
    private boolean ignoreCase;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user;
        user = ignoreCase ? userRepository.findByUsernameIgnoreCase(username):
                userRepository.findByUsername(username);

        if (user == null)
            throw new UsernameNotFoundException(username);
        
        return user;
        
    }
    
} 
