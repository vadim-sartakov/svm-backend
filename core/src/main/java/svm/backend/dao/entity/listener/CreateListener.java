package svm.backend.dao.entity.listener;

import java.time.ZonedDateTime;
import javax.persistence.PrePersist;
import svm.backend.dao.entity.Creatable;

public class CreateListener {
    @PrePersist
    public void setCreatedAt(Object entity) {
        if (entity instanceof Creatable) {
            Creatable creatable = (Creatable) entity;
            creatable.setCreatedAt(ZonedDateTime.now());
        }
    }
}
