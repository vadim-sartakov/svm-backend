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
import svm.backend.signup.validator.UniqueUsername;
import svm.backend.signup.validator.group.SignUp;

@Getter
@Setter
@Entity
public class User extends svm.backend.security.dao.entity.User {
    
    @Valid
    @OneToMany(mappedBy = "user", targetEntity = UserAccount.class, cascade = CascadeType.ALL)
    protected Set<Email> emails = new HashSet<>();
    
    @Valid
    @OneToMany(mappedBy = "user", targetEntity = UserAccount.class, cascade = CascadeType.ALL)
    protected Set<PhoneNumber> phoneNumbers = new HashSet<>();

    @UniqueUsername(groups = SignUp.class)
    @Override
    public String getUsername() {
        return username;
    }
    
}
