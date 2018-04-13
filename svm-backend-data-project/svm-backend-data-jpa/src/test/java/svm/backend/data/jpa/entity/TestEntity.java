package svm.backend.data.jpa.entity;

import java.time.ZonedDateTime;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "TEST_ENTITIES")
public class TestEntity extends UUIDEntity implements Creatable, Updatable {
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
