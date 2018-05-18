package svm.backend.security.jpa.dao.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Embeddable
public class JpaGrantedAuthority implements Serializable, GrantedAuthority {
    
    private Role role; 

    @ManyToOne(optional = false)
    public Role getRole() {
        return role;
    }
    
    @Transient
    @Override
    public String getAuthority() {
        return role.getName();
    }
    
}
