package svm.backend.dao.entity.contact;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import svm.backend.dao.entity.User;

@Data
@Entity
public abstract class Contact implements Serializable {
    
    @Id
    private String contact;
    
    @ManyToOne(optional = false)
    private User user;
    
    private Boolean confirmed;
    
}
