package svm.backend.dao.entity.contact;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import lombok.Data;
import svm.backend.dao.entity.User;

@Data
@Entity
@Inheritance
@DiscriminatorColumn(name = "TYPE")
public abstract class Contact implements Serializable {
    
    @Id
    protected String contact;
    
    @ManyToOne(optional = false)
    protected User user;
    
    protected Boolean confirmed;
    
}
