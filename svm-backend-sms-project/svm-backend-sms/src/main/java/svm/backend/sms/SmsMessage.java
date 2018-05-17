package svm.backend.sms;

import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SmsMessage {
    
    private Long id;
    private String externalId;
    private String phoneNumber;
    private String text;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    
    @Builder.Default
    private Status status = Status.SENDING;
    
    public enum Status {
        SENDING,
        SENT,
        NOT_SENT,
        DELIVERED,
        NOT_DELIVERED,
        ERROR,
        CANCELLED
    }
            
}
