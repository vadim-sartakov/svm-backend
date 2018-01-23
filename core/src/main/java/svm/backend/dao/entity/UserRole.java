package svm.backend.dao.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserRole implements Serializable {
    
    public static final String ADMIN = "ROLE_ADMIN";
    
    @Id
    private String name;
    
}
