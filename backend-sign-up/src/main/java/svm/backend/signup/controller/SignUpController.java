package svm.backend.signup.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.security.dao.repository.UserRepository;
import svm.backend.signup.controller.password.EmailPasswordController;
import svm.backend.signup.controller.password.EmailPasswordController.PasswordRequest;
import svm.backend.signup.controller.password.EmailPasswordController.PasswordRequest.Type;
import svm.backend.signup.dao.entity.User;
import svm.backend.signup.group.PhonePasswordGroup;
import svm.backend.signup.group.SignUpGroup;
import svm.backend.signup.service.SignUpUserFactory;
import svm.backend.web.utils.WebUtils;

@RestController
@RequestMapping("${svm.backend.signup.controller.sign-up-url}")
public class SignUpController {
        
    @Autowired private SignUpUserFactory signUpUserFactory;
    @Autowired private UserRepository userRepository;
    @Autowired private EmailPasswordController emailPasswordController;
    
    @PutMapping
    public void validate(@RequestBody String userJson) {
        signUpUserFactory.parseUser(userJson, Default.class, SignUpGroup.class);        
    }
    
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody String userJson, HttpServletRequest request) {
        
        User user = signUpUserFactory.parseUser(
                userJson,
                Default.class,
                SignUpGroup.class,
                PhonePasswordGroup.class
        );
        
        if (user.getEmails().size() > 0 && user.getPhoneNumbers().isEmpty())
            user.setDisabled(true);
        
        user.getEmails().forEach(account ->
                emailPasswordController.sendPasswordEmail(
                        new PasswordRequest(account.getAccount(), Type.ACTIVATE),
                        WebUtils.getBaseURL(request)
                )
        );
        
        userRepository.superSave(user);
        
    }
    
}
