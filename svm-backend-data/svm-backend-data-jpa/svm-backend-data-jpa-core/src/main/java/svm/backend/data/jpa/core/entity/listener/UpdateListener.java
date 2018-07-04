package svm.backend.data.jpa.core.entity.listener;

import svm.backend.data.jpa.core.entity.Updatable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.ZonedDateTime;

public class UpdateListener {
    
    @PrePersist
    @PreUpdate
    public void setUpdatedAt(Object entity) {
        if (entity instanceof Updatable) {
            Updatable updatable = (Updatable) entity;
            updatable.setUpdatedAt(ZonedDateTime.now());
        }
    }
    
}
