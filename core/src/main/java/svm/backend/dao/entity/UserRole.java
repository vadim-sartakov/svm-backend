package svm.backend.dao.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "USER_ROLES")
@Entity
public class UserRole extends UUIDEntity {
    
    public static final String ADMIN = "ROLE_ADMIN";
        
    @Column(nullable = false, name = "role_id", length = 100)
    private String role;
    
    @ManyToOne(optional = false)
    private User user;
        
    public static UserRole newInstance(User user, String role) {
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);
        return userRole; 
    }
        
}
