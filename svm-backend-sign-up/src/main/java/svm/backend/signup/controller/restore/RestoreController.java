package svm.backend.signup.controller.restore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.security.dao.repository.UserRepository;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.dao.entity.User;
import svm.backend.signup.dao.entity.user.account.UserAccount;
import svm.backend.signup.dao.repository.TemporalPasswordRepository;

@RestController
@RequestMapping("${svm.backend.signup.controller.restore-url}")
public abstract class RestoreController {
         
    protected final Logger logger = LoggerFactory.getLogger(RestoreController.class);
        
    @Autowired protected TemporalPasswordRepository passwordRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    
    @Transactional
    protected void restore(TemporalPassword temporalPassword, String newPassword) {
        
        UserAccount account = temporalPassword.getUserAccount();
        User user = account.getUser();
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordRepository.delete(temporalPassword);
        logger.info("Successfully restored password for account {}", account.getAccount());
        
    }
    
}
