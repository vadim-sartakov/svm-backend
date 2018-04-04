package svm.backend.signup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import svm.backend.signup.dao.entity.User;

public class SignUpUserFactory {
    
    @Autowired protected ObjectMapper mapper;
    @Autowired protected Validator validator;
    
    public User parseUser(String userJson) {
        
        User parsedUser;
        try {
            // TODO: implement view
            parsedUser = mapper.readValue(userJson, getUserClass());
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        Set<ConstraintViolation<Object>> violations = validator.validate(parsedUser);
        if (violations.size() > 0)
            throw new ConstraintViolationException(violations);
        
        return parsedUser;
        
    }
    
    @SuppressWarnings("unchecked")
    protected Class<? extends User> getUserClass() {
        return User.class;
    }
    
}
