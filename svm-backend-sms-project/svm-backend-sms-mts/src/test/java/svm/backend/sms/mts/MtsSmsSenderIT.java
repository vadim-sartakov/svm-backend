package svm.backend.sms.mts;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import svm.backend.sms.SmsMessage;
import svm.backend.sms.SmsSender;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MtsSmsSenderIT {
    
    @Autowired private SmsSender smsSender;
    
    @Test
    public void testSendAndUpdateStatus() {
        SmsMessage message = SmsMessage.builder()
                .phoneNumber("79028284504")
                .text("test")
                .build();
        SmsMessage sentMessage = smsSender.send(message);
        SmsMessage updatedMessage = smsSender.updateMessageStatus(sentMessage);
        assertNotNull(updatedMessage.getUpdatedAt());
        System.out.println(updatedMessage);
    }
        
}
