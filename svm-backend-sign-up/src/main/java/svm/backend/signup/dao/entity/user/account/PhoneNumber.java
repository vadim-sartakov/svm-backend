package svm.backend.signup.dao.entity.user.account;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.data.entity.validator.UniqueValues;
import svm.backend.data.entity.validator.UniqueValues.Field;

@Getter
@Setter
@Entity
@UniqueValues(fields = @Field(
        value = "account",
        message = "{svm.backend.signup.dao.entity.user.account.PhoneNumber.UniqueValues.message}")
)
public class PhoneNumber extends UserAccount {
        
    @NotEmpty
    @svm.backend.signup.validator.PhoneNumber
    @Override
    public String getAccount() {
        return account;
    }
    
}
