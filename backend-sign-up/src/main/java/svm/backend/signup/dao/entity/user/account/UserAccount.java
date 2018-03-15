package svm.backend.signup.dao.entity.user.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import svm.backend.data.entity.Creatable;
import svm.backend.data.entity.UUIDEntity;
import svm.backend.data.entity.validator.UniqueValues;
import svm.backend.data.entity.validator.UniqueValues.Field;
import svm.backend.signup.dao.entity.User;

@Getter
@Setter
@Entity
@Table(name = "USER_ACCOUNTS")
@UniqueValues(@Field(
        value = "account",
        message = "{svm.backend.signup.dao.entity.user.account.UniqueValues.message}")
)
public abstract class UserAccount extends UUIDEntity implements Creatable {
    
    @Column(nullable = false)
    protected ZonedDateTime createdAt;
    
    @NotNull
    @Column(nullable = false)
    protected String account;
    
    @ManyToOne(optional = false)
    protected User user;
        
    @JsonIgnore
    @NotNull
    @Column(nullable = false)
    protected Boolean confirmed = false;
    
}
