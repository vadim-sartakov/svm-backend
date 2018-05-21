package svm.backend.security.jpa.dao.entity;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Embeddable
@Access(AccessType.FIELD)
public class JpaGrantedAuthority implements Serializable, GrantedAuthority {
        
    @Valid
    @ManyToOne(optional = false)
    private Role role; 

    @Override
    public String getAuthority() {
        return role.getName();
    }
    
}
