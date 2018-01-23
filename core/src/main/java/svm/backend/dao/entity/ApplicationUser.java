package svm.backend.dao.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
//@Table(name = "USERS")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedEntityGraph(name = "users.overview", attributeNodes = @NamedAttributeNode("roles"))
public abstract class ApplicationUser extends UUIDEntity {
    
    @OneToMany(mappedBy = "user")
    private List<UserRole> roles;

    @Column(nullable = false, length = 50)
    private String password;
    
    public void addRole(UserRole role) {
        if (roles == null)
            roles = new ArrayList<>();
        roles.add(role);
    }
    
    public boolean isInRole(UserRole roleToFind) {
        for (UserRole currentRole : roles)
            if (currentRole.equals(roleToFind))
                return true;
        return false;
    }
    
}
