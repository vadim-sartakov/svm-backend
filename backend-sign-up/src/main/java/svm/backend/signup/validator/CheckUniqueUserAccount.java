package svm.backend.signup.validator;

import com.querydsl.core.types.dsl.StringPath;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import svm.backend.signup.dao.entity.QUser;
import svm.backend.signup.dao.entity.user.account.UserAccount;
import svm.backend.signup.dao.repository.UserAccountRepository;

public class CheckUniqueUserAccount extends CheckUniqueUser {

    @Autowired protected UserAccountRepository userAccountRepository;
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        
        StringPath path = QUser.user.username;
        UserAccount alreadyRegisteredAccount = userAccountRepository.findOne(
                path.equalsIgnoreCase(value)
        );
        
        return checkIsUnique(alreadyRegisteredAccount, value, context);
        
    }
    
}
