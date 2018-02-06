package svm.backend.userselfservice.controller;

import javax.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import svm.backend.dao.entity.User;
import svm.backend.dao.repository.UserRepository;
import svm.backend.userselfservice.group.PhonePasswordGroup;
import svm.backend.userselfservice.group.SignUpGroup;
import svm.backend.userselfservice.service.SignUpUserFactory;

@RestController
@RequestMapping("${svm.backend.userselfservice.controller.sign-up-url}")
public class SignUpController {
        
    @Autowired private SignUpUserFactory signUpUserFactory;
    @Autowired private UserRepository userRepository;
    
    @PostMapping("validator")
    public void validate(@RequestBody String userJson) {
        signUpUserFactory.parseAndValidateUser(userJson, Default.class, SignUpGroup.class);        
    }
    
    @PostMapping
    public void signUp(@RequestBody String userJson) {
        
        User user = signUpUserFactory.parseAndValidateUser(
                userJson,
                Default.class,
                SignUpGroup.class,
                PhonePasswordGroup.class
        );
        
        userRepository.superSave(user);
        
    }
    
}
