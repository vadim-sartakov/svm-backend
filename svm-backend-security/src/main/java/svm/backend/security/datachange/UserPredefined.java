package svm.backend.security.datachange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import svm.backend.security.dao.entity.User;
import svm.backend.security.dao.entity.UserRole;
import svm.backend.security.dao.repository.UserRepository;
import svm.backend.datachange.DataChangeRollback;
import svm.backend.datachange.DataChangeUpdate;

/**
 * Provides predefined system users.
 * @author Sartakov
 */
public class UserPredefined implements DataChangeUpdate, DataChangeRollback {
        
    @Value("${dao.datachange.userPredefined.shouldRollback:false}")
    private boolean shouldRollback;
    
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    
    @Override
    public String getId() {
        return "UserPredefined";
    }
    
    @Override
    public void update() {
        
        User admin = new User();
        admin.setId(User.ADMIN.getId());
        admin.setUsername("admin");
        admin.addRole(UserRole.ADMIN);
        admin.setPassword(passwordEncoder.encode("123456"));
        
        userRepository.superSave(admin);
        
    }

    @Override
    public boolean shouldRollback() {
        return shouldRollback;
    }
    
    @Override
    public void rollback() {
        userRepository.superDelete(User.ADMIN.getId());
    }
    
}
