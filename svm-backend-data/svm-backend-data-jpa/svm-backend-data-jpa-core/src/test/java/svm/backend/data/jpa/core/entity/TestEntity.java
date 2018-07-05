package svm.backend.data.jpa.core.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter

@Entity
@Table(name = "TEST_ENTITIES")
public class TestEntity implements Identifiable, Creatable, Updatable, Serializable {
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.core.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;
    
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
