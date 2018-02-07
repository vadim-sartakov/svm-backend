package svm.backend.userselfservice.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EMAIL_PASSWORDS")
public class EmailPassword extends TemporalPassword {

    @Column(unique = true)
    @Override
    public String getPassword() {
        return password;
    }
    
}
