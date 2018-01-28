package svm.backend.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    public static final String MODERATOR = "ROLE_MODERATOR";
        
    @Column(nullable = false, name = "role_id", length = 100)
    private String role;
    
    @ManyToOne(optional = false)
    private User user;
        
    public static UserRole of(String role) {
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        return userRole; 
    }
        
}
