package svm.backend.dao.entity.useraccount;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class PhoneNumber extends UserAccount {
    
    @NotEmpty
    @Pattern(
            regexp = "^\\+\\d{1,2}\\(\\d{3}\\)\\d{3}\\-\\d{2}\\-\\d{2}$",
            message = "{svm.backend.dao.entity.useraccount.PhoneNumber.message}")
    @Override
    public String getAccount() {
        return account;
    }
    
}
