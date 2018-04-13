package svm.backend.sms;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsMessage {
    
    private String phoneNumber;
    private String text;
    
    public SmsMessage(SmsMessage source) {
        this.phoneNumber = source.getPhoneNumber();
        this.text = source.getText();
    }
            
}
