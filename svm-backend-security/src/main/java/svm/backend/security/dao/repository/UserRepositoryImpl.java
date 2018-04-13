package svm.backend.security.dao.repository;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import svm.backend.security.dao.entity.User;
import svm.backend.security.dao.entity.UserRole;
import svm.backend.security.service.AuthenticationInfo;

public class UserRepositoryImpl {
    
    @Autowired private UserRepository userRepository;
    @Autowired private AuthenticationInfo authenticationInfo;
    @Autowired private PasswordEncoder passwordEncoder;
    
    public User findOne(UUID id) {
        if (authenticationInfo.isUserInRole(UserRole.ADMIN) ||
                authenticationInfo.getCurrentUser().getId().equals(id))
            return userRepository.superFindOne(id);
        else
            return null;
    }
    
    // TODO: implement controllers and track if password field has been changed.
    public User save(User user) {        
        if (user.getId() == null)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.superSave(user);
    }
    
    public void delete(UUID id) {
        // TODO: do not delete if it's last admin user.
        userRepository.superDelete(id);
    }
    
}
