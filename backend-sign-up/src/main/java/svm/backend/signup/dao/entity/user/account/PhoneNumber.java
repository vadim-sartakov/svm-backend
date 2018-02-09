package svm.backend.signup.dao.entity.user.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.signup.dao.entity.validator.RegexPatterns;

@Getter
@Setter
@Entity
public class PhoneNumber extends UserAccount {
    
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private String password;
    
    @NotEmpty
    @Pattern(
            regexp = RegexPatterns.MOBILE_PHONE_PATTERN,
            message = RegexPatterns.WRONG_MOBILE_PHONE_MESSAGE)
    @Override
    public String getAccount() {
        return account;
    }
    
}
