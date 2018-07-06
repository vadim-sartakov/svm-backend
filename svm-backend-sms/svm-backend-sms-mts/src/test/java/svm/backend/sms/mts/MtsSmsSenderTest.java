package svm.backend.sms.mts;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import svm.backend.sms.core.SmsMessage;
import svm.backend.sms.core.SmsStatus;
import svm.backend.sms.mts.config.MtsProperties;
import svm.backend.sms.mts.model.ArrayOfDeliveryInfo;
import svm.backend.sms.mts.model.SendMessageResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class MtsSmsSenderTest {
        
    private final RestTemplateBuilder restTemplateBuilder = Mockito.mock(RestTemplateBuilder.class);
    private final RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
    private final MtsProperties properties = Mockito.mock(MtsProperties.class);
    
    private MtsSmsSender smsSender;
        
    @Before
    public void setUp() {
        Mockito.when(restTemplateBuilder.build()).thenReturn(restTemplate);
        this.smsSender = new MtsSmsSender(restTemplateBuilder, properties);
        Mockito.when(properties.getSendUrl()).thenReturn("");
        Mockito.when(properties.getGetStatusUrl()).thenReturn("");
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
        String id = smsSender.send(message);
        assertThat(id, is("123456"));
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
        SmsStatus expectedStatus = SmsStatus.builder()
                .updatedAt(ZonedDateTime.of(2018, 5, 17, 11, 0, 00, 0, ZoneId.systemDefault()))
                .state(SmsStatus.State.DELIVERED)
                .build();
        SmsStatus actualStatus = smsSender.getMessageStatus("123");
        assertEquals(expectedStatus, actualStatus);
    }
        
}
