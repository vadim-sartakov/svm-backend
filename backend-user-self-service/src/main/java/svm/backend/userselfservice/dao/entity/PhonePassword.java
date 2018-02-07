package svm.backend.userselfservice.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PHONE_PASSWORDS")
public class PhonePassword extends TemporalPassword {

    @Column(nullable = false)
    private Integer attempts = 0;
    
}
