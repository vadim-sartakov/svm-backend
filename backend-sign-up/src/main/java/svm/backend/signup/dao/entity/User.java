package svm.backend.signup.dao.entity;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import svm.backend.signup.dao.entity.user.account.Email;
import svm.backend.signup.dao.entity.user.account.PhoneNumber;
import svm.backend.signup.dao.entity.user.account.UserAccount;
import svm.backend.signup.validator.UniqueValues;
import svm.backend.signup.validator.UniqueValues.UniqueValue;
import svm.backend.signup.validator.group.SignUp;

@Getter
@Setter
@UniqueValues(groups = SignUp.class, value = @UniqueValue(field = "username", ignoreCaseExpr = "${svm.backend.security.findUserIgnoreCase}"))
@Entity
public class User extends svm.backend.security.dao.entity.User {
    
    @Valid
    @OneToMany(mappedBy = "user", targetEntity = UserAccount.class, cascade = CascadeType.ALL)
    protected Set<Email> emails = new HashSet<>();
    
    @Valid
    @OneToMany(mappedBy = "user", targetEntity = UserAccount.class, cascade = CascadeType.ALL)
    protected Set<PhoneNumber> phoneNumbers = new HashSet<>();
    
}
