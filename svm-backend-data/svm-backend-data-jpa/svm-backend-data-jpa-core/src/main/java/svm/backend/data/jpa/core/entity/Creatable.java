package svm.backend.data.jpa.core.entity;

import java.time.ZonedDateTime;

public interface Creatable {
    ZonedDateTime getCreatedAt();
    void setCreatedAt(ZonedDateTime createdAt);
}
