package svm.backend.sms.mts;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import svm.backend.sms.SmsMessage;
import svm.backend.sms.SmsSender;
import svm.backend.sms.mts.config.MtsProperties;
import svm.backend.sms.mts.model.SendMessageResponse;
import svm.backend.sms.mts.model.ArrayOfDeliveryInfo;

public class MtsSmsSender implements SmsSender<Long, ArrayOfDeliveryInfo> {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(MtsSmsSender.class);
    
    @Autowired private RestTemplate restTemplate;
    @Autowired private MtsProperties properties;
        
    @Override
    public Long send(SmsMessage message) {
        
        String phoneNumber = message.getPhoneNumber()
                .replaceAll("\\+(\\d)", "7")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("-", "");
        
        Map<String, String> params = new HashMap<>();
        params.put("msid", phoneNumber);
        params.put("message", message.getText());
        params.put("naming", properties.getNaming());
        params.put("login", properties.getLogin());
        params.put("password", properties.getEncodedPassword());
        
        ResponseEntity<SendMessageResponse> response;
        try {
            response = restTemplate.postForEntity(properties.getSendUrl(), null, SendMessageResponse.class, params);
        } catch (RestClientException ex) {
            LOGGER.error("Failed to send message to {}", phoneNumber, ex);
            throw ex;
        }

        long messageId = response.getBody().getId();
        LOGGER.debug("SMS to {} has been sent successfully with id {}", phoneNumber, messageId);
        
        return messageId;
        
    }
        
    @Override
    public ArrayOfDeliveryInfo getMessageStatus(Long id) {
        
        Map<String, String> params = new HashMap<>();
        params.put("messageId", Long.toString(id));
        params.put("login", properties.getLogin());
        params.put("password", properties.getPassword());
        
        ArrayOfDeliveryInfo statusList;
        
        try {
            statusList = restTemplate.getForObject(properties.getGetStatusUrl(), ArrayOfDeliveryInfo.class, params);
        } catch (RestClientException ex) {
            LOGGER.error("Failed to get message status for {}", id);
            throw ex;
        }
        
        return statusList;
        
    }
                    
}
