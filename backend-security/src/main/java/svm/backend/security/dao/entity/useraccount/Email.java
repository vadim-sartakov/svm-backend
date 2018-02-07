package svm.backend.security.dao.entity.useraccount;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Email extends UserAccount {

    @NotEmpty
    @Pattern(
            regexp = "^([A-Za-z0-9_\\.-]+\\@[\\dA-Za-z\\.-]+\\.[a-z\\.]{2,6})$",
            message = "{svm.backend.dao.entity.useraccount.Email.message}")
    @Override
    public String getAccount() {
        return account;
    }
    
}
