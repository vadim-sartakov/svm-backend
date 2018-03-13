package svm.backend.signup.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.security.dao.repository.UserRepository;
import svm.backend.signup.dao.entity.User;
import svm.backend.signup.service.EmailPasswordSender;
import svm.backend.signup.service.PhonePasswordSender;
import svm.backend.signup.service.SignUpUserFactory;

@RestController
@RequestMapping("${svm.backend.signup.controller.sign-up-url}")
public class SignUpController {
        
    @Value("${svm.backend.signup.controller.sign-up-url}")
    protected String signUpUrl;
    
    @Autowired private SignUpUserFactory signUpUserFactory;
    @Autowired private EmailPasswordSender emailPasswordSender;
    @Autowired private PhonePasswordSender phonePasswordSender;
    @Autowired private UserRepository userRepository;
    
    @PutMapping
    public void validate(@RequestBody String userJson) {
        signUpUserFactory.parseUser(userJson);        
    }
    
    @Transactional
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody String userJson, HttpServletRequest request) {
        
        User user = signUpUserFactory.parseUser(userJson);
        
        user.setDisabled(true);
        userRepository.save(user);
        
        user.getPhoneNumbers().forEach(account ->
                phonePasswordSender.sendMessage(account.getAccount())
        );
        
        user.getEmails().forEach(account ->
                emailPasswordSender.sendMessage(
                        account.getAccount(),
                        signUpUrl,
                        "svm.backend.signup.controller.EmailPasswordController.activateSubject",
                        "svm.backend.signup.controller.EmailPasswordController.activateMessage",
                        request
                )
        );
        
    }
    
}
