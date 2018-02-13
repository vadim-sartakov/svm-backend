package svm.backend.signup.dao.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EMAIL_PASSWORDS")
@AttributeOverride(
        name = "password",
        column = @Column(nullable = false, unique = true)
)
public class EmailPassword extends TemporalPassword {
    @Override
    public void setAccount(String account) {
        this.account = account.toLowerCase();
    }
}
