package svm.backend.security.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseGrantedAuthority implements Serializable, GrantedAuthority {
        
    protected BaseRole role; 

    @Override
    public String getAuthority() {
        return role.getName();
    }
    
}
