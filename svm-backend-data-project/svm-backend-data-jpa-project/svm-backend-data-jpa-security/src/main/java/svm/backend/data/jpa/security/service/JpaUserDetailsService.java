package svm.backend.data.jpa.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import svm.backend.data.jpa.security.dao.entity.User;
import svm.backend.data.jpa.security.dao.repository.UserRepository;
import svm.backend.security.util.SecurityUtils;

public class JpaUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUtils.setAuthentication(User.SYSTEM);
        User user = repository.findByUsername(username);
        SecurityUtils.restoreAuthentication();
        return user;
    }
    
}
