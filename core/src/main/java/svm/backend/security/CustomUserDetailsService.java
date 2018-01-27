package svm.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import svm.backend.dao.entity.User;
import svm.backend.dao.repository.ContactRepository;
import svm.backend.dao.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private ContactRepository contactRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user;
        user = userRepository.findByUserName(username);
        if (user == null)
            user = contactRepository.findOne(username);
        
        return user;
        
    }
    
}
