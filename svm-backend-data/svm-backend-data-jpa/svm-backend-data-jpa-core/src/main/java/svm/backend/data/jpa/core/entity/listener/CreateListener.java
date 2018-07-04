package svm.backend.data.jpa.core.entity.listener;

import svm.backend.data.jpa.core.entity.Creatable;

import javax.persistence.PrePersist;
import java.time.ZonedDateTime;

public class CreateListener {
    @PrePersist
    public void setCreatedAt(Object entity) {
        if (entity instanceof Creatable) {
            Creatable creatable = (Creatable) entity;
            if (creatable.getCreatedAt() == null)
                creatable.setCreatedAt(ZonedDateTime.now());
        }
    }
}
