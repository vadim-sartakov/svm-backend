package svm.backend.data.jpa.entity;

import java.time.ZonedDateTime;

public interface Expirable {
    ZonedDateTime getExpiresAt();
    void setExpiresAt(ZonedDateTime expiresAt);
}
