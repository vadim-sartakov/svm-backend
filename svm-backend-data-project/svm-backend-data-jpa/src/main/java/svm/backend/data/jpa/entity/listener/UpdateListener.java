package svm.backend.data.jpa.entity.listener;

import java.time.ZonedDateTime;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import svm.backend.data.jpa.entity.Updatable;

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
