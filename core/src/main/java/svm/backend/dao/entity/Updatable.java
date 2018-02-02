package svm.backend.dao.entity;

import java.time.ZonedDateTime;

public interface Updatable {
    ZonedDateTime getUpdatedAt();
    void setUpdatedAt(ZonedDateTime updatedAt);
}
