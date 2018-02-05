package svm.backend.dao.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.dao.entity.useraccount.UserAccount;

@Data
@EqualsAndHashCode(exclude = "userAccount")
@Entity
@Table(name = "TEMPORAL_PASSWORDS")
public class TemporalPassword implements Creatable, Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne(optional = false)
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;
    
    @NotNull
    @Column(nullable = false)
    private ZonedDateTime createdAt;
    
    @Column(nullable = false)
    private Integer attempts = 0;
    
    @NotEmpty
    @Column(nullable = false)
    private String password;
    
}
