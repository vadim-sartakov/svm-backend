package svm.backend.dao.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "name")
@Entity
public class UserRole implements Serializable {
    
    public static final String ADMIN = "ROLE_ADMIN";
    
    @Id
    private String name;
    
    @ManyToOne(optional = false)
    private ApplicationUser user;
        
    public static UserRole newInstance(String name) {
        UserRole userRole = new UserRole();
        userRole.setName(name);
        return userRole; 
    }
    
}
