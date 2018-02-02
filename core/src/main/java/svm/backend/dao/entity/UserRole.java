package svm.backend.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = "role")
@Table(name = "USER_ROLES", uniqueConstraints = @UniqueConstraint(columnNames = { "role_id", "user_id" }))
@Entity
public class UserRole extends UUIDEntity {
    
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String MODERATOR = "ROLE_MODERATOR";
        
    @Column(nullable = false, name = "role_id", length = 100)
    private String role;
    
    @ManyToOne(optional = false)
    private User user;
        
    public static UserRole of(User user, String role) {
        UserRole userRole = UserRole.of(role);
        userRole.setUser(user);
        return userRole;
    }
    
    public static UserRole of(String role) {
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        return userRole; 
    }
        
}
