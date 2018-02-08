package svm.backend.signup.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.security.dao.entity.User;
import svm.backend.security.dao.repository.UserRepository;
import svm.backend.signup.group.PhonePasswordGroup;
import svm.backend.signup.group.SignUpGroup;
import svm.backend.signup.service.TemplatedEmailSender;
import svm.backend.signup.service.SignUpUserFactory;

@RestController
@RequestMapping("${svm.backend.signup.controller.sign-up-url}")
public class SignUpController {
        
    @Autowired private SignUpUserFactory signUpUserFactory;
    @Autowired private UserRepository userRepository;
    
    @Autowired private TemplatedEmailSender emailSender;
    
    @PutMapping
    public void validate(@RequestBody String userJson) {
        signUpUserFactory.parseUser(userJson, Default.class, SignUpGroup.class);        
    }
    
    @PostMapping
    public void signUp(@RequestBody String userJson, HttpServletRequest request) {
        
        User user = signUpUserFactory.parseUser(
                userJson,
                Default.class,
                SignUpGroup.class,
                PhonePasswordGroup.class
        );
        
        user.getEmails().forEach(emailAccount -> {
            emailSender.sendSignUpEmail(emailAccount.getAccount(), request, userJson);
        });
        
        userRepository.superSave(user);
        
    }
    
}
