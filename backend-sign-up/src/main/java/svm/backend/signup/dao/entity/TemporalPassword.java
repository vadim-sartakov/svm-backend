package svm.backend.signup.dao.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.data.entity.Creatable;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class TemporalPassword implements Creatable, Serializable {
    
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
