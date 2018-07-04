package svm.backend.data.jpa.security.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import lombok.NoArgsConstructor;
import svm.backend.security.model.BaseGrantedAuthority;
import svm.backend.security.model.BaseRole;

@Embeddable
@NoArgsConstructor
public class Authority extends BaseGrantedAuthority {
    
    public Authority(Role role) {
        super(role);
    }
    
    @Valid
    @ManyToOne(optional = false, targetEntity = Role.class, cascade = CascadeType.MERGE)
    @Override
    public BaseRole getRole() {
        return role;
    }
        
}
