package svm.backend.data.jpa.entity;

import java.time.ZonedDateTime;

public interface Updatable {
    ZonedDateTime getUpdatedAt();
    void setUpdatedAt(ZonedDateTime updatedAt);
}
