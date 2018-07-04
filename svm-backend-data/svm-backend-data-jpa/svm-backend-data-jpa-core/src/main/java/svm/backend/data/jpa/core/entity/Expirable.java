package svm.backend.data.jpa.core.entity;

import java.time.ZonedDateTime;

public interface Expirable {
    ZonedDateTime getExpiresAt();
    void setExpiresAt(ZonedDateTime expiresAt);
}
