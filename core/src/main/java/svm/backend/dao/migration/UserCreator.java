package svm.backend.dao.migration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import svm.backend.dao.entity.User;
import svm.backend.dao.entity.UserRole;
import svm.backend.dao.repository.UserRepository;

public class UserCreator implements Migration {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    
    @Override
    public String getId() {
        return "SystemPredefined";
    }

    @Override
    public void execute() {
        User user = new User();
        user.addRole(UserRole.ADMIN);
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("123456"));
                
        userRepository.superSave(user);
    }
    
}
