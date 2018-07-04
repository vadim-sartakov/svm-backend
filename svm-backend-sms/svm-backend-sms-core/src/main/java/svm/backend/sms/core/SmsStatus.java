package svm.backend.sms.core;

import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SmsStatus {
    
    private ZonedDateTime updatedAt;
    private State state;
    
    public enum State {
        SENDING,
        DELIVERED,
        ERROR
    }
    
}
