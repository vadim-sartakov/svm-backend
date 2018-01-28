package svm.backend.dao.repository;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import svm.backend.dao.entity.User;
import svm.backend.security.AuthenticationInfo;

public class UserRepositoryImpl {
    
    @Autowired private UserRepository userRepository;
    @Autowired private AuthenticationInfo authenticationInfo;
    @Autowired private PasswordEncoder passwordEncoder;
    
    public User findOne(UUID id) {
        if (authenticationInfo.isUserInRole("ROLE_ADMIN") ||
                authenticationInfo.getCurrentUser().getId().equals(id))
            return userRepository.superFindOne(id);
        else
            return null;
    }
    
    public User save(User user) {
        if (user.getId() == null)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.superSave(user);
    }
    
}
