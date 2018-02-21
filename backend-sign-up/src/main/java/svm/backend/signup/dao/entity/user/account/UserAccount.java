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
import svm.backend.signup.dao.entity.User;
import svm.backend.signup.validator.UniqueUserAccount;
import svm.backend.signup.validator.group.SignUp;

@Getter
@Setter
@Entity
@Table(name = "USER_ACCOUNTS")
public abstract class UserAccount extends UUIDEntity implements Creatable {
    
    @Column(nullable = false)
    protected ZonedDateTime createdAt;
    
    @UniqueUserAccount(groups = SignUp.class)
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
