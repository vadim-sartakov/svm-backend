package svm.backend.sms;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class SmsMessageTest {
    
    @Test
    public void test() {
        SmsMessage source, destination;
        source = new SmsMessage("123456", "test text");
        destination = new SmsMessage(source);
        assertThat(destination.getPhoneNumber(), is(source.getPhoneNumber()));
        assertThat(destination.getText(), is(source.getText()));
    }
    
}
