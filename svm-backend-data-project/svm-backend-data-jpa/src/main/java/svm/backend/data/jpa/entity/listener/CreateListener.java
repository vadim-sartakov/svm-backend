package svm.backend.data.jpa.entity.listener;

import java.time.ZonedDateTime;
import javax.persistence.PrePersist;
import svm.backend.data.jpa.entity.Creatable;

public class CreateListener {
    @PrePersist
    public void setCreatedAt(Object entity) {
        if (entity instanceof Creatable) {
            Creatable creatable = (Creatable) entity;
            creatable.setCreatedAt(ZonedDateTime.now());
        }
    }
}
