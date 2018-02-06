package svm.backend.dao.entity.useraccount;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.dao.entity.TemporalPassword;
import svm.backend.dao.entity.User;

@Data
@Entity
@Table(name = "USER_ACCOUNTS")
@Inheritance
public abstract class UserAccount implements Serializable {
    
    @Id
    @NotNull
    protected String account;
    
    @NotNull
    @ManyToOne(optional = false)
    protected User user;
    
    @OneToOne(optional = false)
    @NotEmpty
    protected TemporalPassword temporalPassword;
        
    @NotNull
    @Column(nullable = false)
    protected Boolean confirmed = false;
    
}
