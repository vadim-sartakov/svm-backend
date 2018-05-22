package svm.backend.data.jpa.security.dao.entity;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.data.jpa.entity.Identifiable;
import svm.backend.security.model.BaseRole;

@Entity(name = "Role")
@Table(name = "ROLES")
public class Role extends BaseRole implements Serializable, Identifiable {
        
    public static final Role SYSTEM = predefined(Role.class, ROLE_SYSTEM);
    public static final Role ADMIN = predefined(Role.class, ROLE_ADMIN);
    public static final Role MODERATOR = predefined(Role.class, ROLE_MODERATOR);
    
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    @Override
    public UUID getId() {
        return id;
    }

    @NotEmpty
    @Column(nullable = false)
    @Override
    public String getName() {
        return name;
    }
        
}
