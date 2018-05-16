package svm.backend.samples.shop.dao.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import svm.backend.data.jpa.entity.Creatable;

@Data
//@Entity
//@Table(name = "USERS")
public class User implements Serializable, Creatable {
    @Id
    @GenericGenerator(name = "uuid", strategy = "svm.backend.data.jpa.generator.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;
    private String username;
    private ZonedDateTime createdAt;
}
