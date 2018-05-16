package svm.backend.sms.mts;

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
    public void testSend() {
        smsSender.send(new SmsMessage("79028284504", "test"));
    }
    
}
