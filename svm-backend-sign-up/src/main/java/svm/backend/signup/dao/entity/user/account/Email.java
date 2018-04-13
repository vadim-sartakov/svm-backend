package svm.backend.signup.dao.entity.user.account;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.data.entity.validator.UniqueValues;
import svm.backend.data.entity.validator.UniqueValues.Field;

@Entity
@UniqueValues(fields = @Field(
        value = "account",
        message = "{svm.backend.signup.dao.entity.user.account.Email.UniqueValues.message}")
)
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
