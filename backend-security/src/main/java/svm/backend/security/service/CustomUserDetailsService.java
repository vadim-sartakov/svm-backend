package svm.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import svm.backend.security.dao.entity.User;
import svm.backend.security.dao.entity.useraccount.UserAccount;
import svm.backend.security.dao.repository.UserRepository;
import svm.backend.security.dao.repository.UserAccountRepository;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private UserAccountRepository contactRepository;
    
    @Value("${svm.backend.security.findUserIgnoreCase:true}")
    private boolean ignoreCase;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user;
        username = username.toLowerCase();
        user = ignoreCase ? userRepository.findByUsernameIgnoreCase(username):
                userRepository.findByUsername(username);
        if (user == null) {
            UserAccount contact = contactRepository.findByAccountIgnoreCase(username);
            if (contact != null)
                user = contact.getUser();
        }
        
        if (user == null)
            throw new UsernameNotFoundException(username);
        
        return user;
        
    }
    
} 
