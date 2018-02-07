package svm.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import svm.backend.security.dao.entity.User;
import svm.backend.security.dao.entity.useraccount.UserAccount;
import svm.backend.security.dao.repository.ContactRepository;
import svm.backend.security.dao.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;
    @Autowired private ContactRepository contactRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user;
        username = username.toLowerCase();
        user = userRepository.findByUsername(username);
        if (user == null) {
            UserAccount contact = contactRepository.findOne(username);
            if (contact != null)
                user = contact.getUser();
        }
        
        if (user == null)
            throw new UsernameNotFoundException(username);
        
        return user;
        
    }
    
    } 
