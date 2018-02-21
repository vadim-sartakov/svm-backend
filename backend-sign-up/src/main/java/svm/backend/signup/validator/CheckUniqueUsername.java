package svm.backend.signup.validator;

import com.querydsl.core.types.dsl.StringPath;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import svm.backend.security.dao.repository.UserRepository;
import svm.backend.signup.dao.entity.QUser;
import svm.backend.signup.dao.entity.User;

public class CheckUniqueUsername extends CheckUniqueUser {

    @Autowired protected UserRepository userRepository;
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        StringPath path = QUser.user.username;
        User alreadyRegisteredUser = (User) userRepository.findOne(
                ignoreCase ? path.equalsIgnoreCase(value) : path.eq(value)
        );
        
        return checkIsUnique(alreadyRegisteredUser, value, context);
        
    }
    
}
