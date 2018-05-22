package svm.backend.data.jpa.security.dao.entity;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import svm.backend.security.model.BaseGrantedAuthority;
import svm.backend.security.model.BaseRole;

@Embeddable
@NoArgsConstructor
@Access(AccessType.PROPERTY)
public class JpaGrantedAuthority extends BaseGrantedAuthority implements Serializable, GrantedAuthority {
    
    public JpaGrantedAuthority(BaseRole role) {
        super(role);
    }
    
    @Valid
    @ManyToOne(optional = false, targetEntity = Role.class)
    @Override
    public BaseRole getRole() {
        return role;
    }
    
    @Transient
    @Override
    public String getAuthority() {
        return role.getName();
    }
    
}
