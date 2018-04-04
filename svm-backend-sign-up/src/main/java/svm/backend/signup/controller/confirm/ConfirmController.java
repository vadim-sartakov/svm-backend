package svm.backend.signup.controller.confirm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.security.dao.repository.UserRepository;
import svm.backend.signup.dao.entity.TemporalPassword;
import svm.backend.signup.dao.entity.User;
import svm.backend.signup.dao.entity.user.account.UserAccount;
import svm.backend.signup.dao.repository.TemporalPasswordRepository;

@RestController
@RequestMapping("${svm.backend.signup.controller.sign-up-url}/confirm")
public abstract class ConfirmController {
    
    @Autowired protected UserRepository userRepository;
    @Autowired protected TemporalPasswordRepository passwordRepository;
    
    protected void confirm(TemporalPassword temporalPassword) {
        
        UserAccount account = temporalPassword.getUserAccount();
        account.setConfirmed(true);
        
        User user = account.getUser();
        user.setDisabled(false);
        
        passwordRepository.delete(temporalPassword);
        userRepository.superSave(user);
        
    }
    
}
