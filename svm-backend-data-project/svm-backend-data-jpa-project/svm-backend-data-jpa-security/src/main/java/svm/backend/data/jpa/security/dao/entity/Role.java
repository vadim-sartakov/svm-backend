package svm.backend.data.jpa.security.dao.entity;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
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
    
    public static final String SYSTEM = "ROLE_SYSTEM";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String MODERATOR = "ROLE_MODERATOR";
    
    public static final Map<String, Role> PREDEFINED = new HashMap<>();
    
    static {
        PREDEFINED.put(SYSTEM, Role.builder()
                .id(UUID.nameUUIDFromBytes(SYSTEM.getBytes(StandardCharsets.UTF_8)))
                .name(SYSTEM).build());
        PREDEFINED.put(ADMIN, Role.builder()
                .id(UUID.nameUUIDFromBytes(ADMIN.getBytes(StandardCharsets.UTF_8)))
                .name(ADMIN).build());
        PREDEFINED.put(MODERATOR, Role.builder()
                .id(UUID.nameUUIDFromBytes(MODERATOR.getBytes(StandardCharsets.UTF_8)))
                .name(MODERATOR).build());
    }
    
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    @Column(length = 16)
    private UUID id;
    
    @NotEmpty
    @Column(nullable = false)
    private String name;
    
}
