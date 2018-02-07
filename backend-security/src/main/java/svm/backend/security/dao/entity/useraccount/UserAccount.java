package svm.backend.security.dao.entity.useraccount;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import svm.backend.security.dao.entity.User;

@Data
@Entity
@Table(name = "USER_ACCOUNTS")
@Inheritance
public abstract class UserAccount implements Serializable {
    
    @Id
    @NotNull
    protected String account;
    
    @ManyToOne
    protected User user;
        
    @JsonIgnore
    @NotNull
    @Column(nullable = false)
    protected Boolean confirmed = false;
    
}
