package svm.backend.sms.mts;

import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import svm.backend.sms.SmsMessage;
import svm.backend.sms.mts.config.MtsProperties;
import svm.backend.sms.mts.model.ArrayOfDeliveryInfo;
import svm.backend.sms.mts.model.SendMessageResponse;

public class MtsSmsSenderTest {
        
    private final RestTemplateBuilder restTemplateBuilder = Mockito.mock(RestTemplateBuilder.class);
    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    
    private final MtsSmsSender smsSender = new MtsSmsSender(restTemplateBuilder, Mockito.mock(MtsProperties.class));
        
    @Before
    public void setUp() {
        Mockito.when(restTemplateBuilder.build()).thenReturn(restTemplate);
        smsSender.initialize();
    }
        
    @SuppressWarnings("unchecked")
    @Test
    public void testSend() throws Exception {
        SendMessageResponse response = parse(SendMessageResponse.class, "/templates/SendMessage.xml");
        Mockito.when(restTemplate.postForObject(any(String.class), any(Object.class), any(Class.class)))
                .thenReturn(response);
        SmsMessage message = SmsMessage.builder()
                .phoneNumber("123")
                .text("test")
                .build();
        SmsMessage sentMessage = smsSender.send(message);
        assertThat(sentMessage.getId(), is("123456"));
    }

    @SuppressWarnings("unchecked")
    private <T> T parse(Class<T> type, String path) throws Exception {
        JAXBContext context = JAXBContext.newInstance(type);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        InputStream input = getClass().getResourceAsStream(path);
        return (T) unmarshaller.unmarshal(input);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void testGetMessageStatus() throws Exception {
        Mockito.when(restTemplate.postForObject(any(String.class), any(Object.class), any(Class.class)))
                .thenReturn(parse(ArrayOfDeliveryInfo.class, "/templates/GetMessageStatus.xml"));
        SmsMessage expectedMessage = SmsMessage.builder()
                .id("123")
                .phoneNumber("495")
                .createdAt(ZonedDateTime.of(2018, 5, 17, 10, 30, 00, 0, ZoneId.systemDefault()))
                .updatedAt(ZonedDateTime.of(2018, 5, 17, 11, 0, 00, 0, ZoneId.systemDefault()))
                .status(SmsMessage.Status.DELIVERED)
                .build();
        SmsMessage actualMessage = smsSender.getMessageStatus(SmsMessage.builder().id("123").build());
        assertEquals(expectedMessage, actualMessage);
    }
        
}
