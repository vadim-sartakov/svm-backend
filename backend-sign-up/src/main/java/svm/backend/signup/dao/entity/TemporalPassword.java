package svm.backend.signup.dao.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.data.entity.Creatable;
import svm.backend.data.entity.Expirable;

@Data
@MappedSuperclass
public abstract class TemporalPassword implements Creatable, Expirable, Serializable {
    
    @Id
    @Column(unique = true)
    protected String account;
    
    @NotNull
    @Column(nullable = false)
    protected ZonedDateTime createdAt;
    
    @NotNull
    @Column(nullable = false)
    protected ZonedDateTime expiresAt;
    
    @NotEmpty
    @Column(nullable = false)
    protected String password;
        
}
