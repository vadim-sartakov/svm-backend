package svm.backend.security.dao.entity.useraccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

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
            regexp = "^\\+\\d{1,2}\\(\\d{3}\\)\\d{3}\\-\\d{2}\\-\\d{2}$",
            message = "{svm.backend.dao.entity.useraccount.PhoneNumber.message}")
    @Override
    public String getAccount() {
        return account;
    }
    
}
