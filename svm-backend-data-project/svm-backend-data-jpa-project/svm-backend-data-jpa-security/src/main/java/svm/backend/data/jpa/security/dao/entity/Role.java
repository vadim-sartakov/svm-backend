package svm.backend.data.jpa.security.dao.entity;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;
import svm.backend.data.jpa.entity.Identifiable;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
@Entity
@Table(name = "ROLES")
public class Role implements Serializable, Identifiable {
    
    public static final String ROLE_SYSTEM = "ROLE_SYSTEM";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    
    public static final Role SYSTEM = createRole(ROLE_SYSTEM);
    public static final Role ADMIN = createRole(ROLE_ADMIN);
    public static final Role MODERATOR = createRole(ROLE_MODERATOR);
        
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    private UUID id;
    
    @NotEmpty
    @Column(nullable = false)
    private String name;
    
    private static Role createRole(String role) {
        return Role.builder()
                .id(UUID.nameUUIDFromBytes(role.getBytes(StandardCharsets.UTF_8)))
                .name(role).build();
    }
    
}
