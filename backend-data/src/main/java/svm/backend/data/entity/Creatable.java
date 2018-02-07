package svm.backend.data.entity;

import java.time.ZonedDateTime;

public interface Creatable {
    ZonedDateTime getCreatedAt();
    void setCreatedAt(ZonedDateTime createdAt);
}
