package svm.backend.security.model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public abstract class BaseRole implements Serializable {
    
    public static final String ROLE_SYSTEM = "ROLE_SYSTEM";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
        
    protected UUID id;
    protected String name;
    
    public static <T extends BaseRole> T predefined(Class<T> type, String roleName) {
        T role = BeanUtils.instantiate(type);
        role.id = UUID.nameUUIDFromBytes(roleName.getBytes(StandardCharsets.UTF_8));
        role.name = roleName;
        return role;
    }
    
}
