package svm.backend.signup.dao.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.data.entity.Creatable;
import svm.backend.data.entity.Expirable;
import svm.backend.data.entity.UUIDEntity;
import svm.backend.signup.dao.entity.user.account.UserAccount;

@Data
@Entity
@Table(name = "TEMPORAL_PASSWORDS")
public class TemporalPassword extends UUIDEntity implements Creatable, Expirable, Serializable {
        
    @OneToOne(optional = false)
    private UserAccount userAccount;
    
    @NotNull
    @Column(nullable = false)
    private ZonedDateTime createdAt;
    
    @NotNull
    @Column(nullable = false)
    private ZonedDateTime expiresAt;
    
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String password;
    
    private Integer attempts = 0;
    
}
