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
import svm.backend.signup.controller.activate.EmailActivateController;
import svm.backend.signup.controller.activate.PhoneActivateController;
import svm.backend.signup.dao.entity.User;
import svm.backend.signup.group.SignUpGroup;
import svm.backend.signup.service.SignUpUserFactory;

@RestController
@RequestMapping("${svm.backend.signup.controller.sign-up-url}")
public class SignUpController {
        
    @Autowired private SignUpUserFactory signUpUserFactory;
    @Autowired private UserRepository userRepository;
    @Autowired private EmailActivateController emailController;
    @Autowired private PhoneActivateController phoneController;
    
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
                SignUpGroup.class
        );
        
        user.setDisabled(true);
        userRepository.save(user);
        
        user.getPhoneNumbers().forEach(account ->
                phoneController.sendMessage(
                        new PhoneActivateController.Request(account.getAccount())
                )
        );
        
        user.getEmails().forEach(account ->
                emailController.sendEmailPassword(
                        new EmailActivateController.Request(account.getAccount()),
                        request
                )
        );
        
    }
    
}
