package svm.backend.sms.mts;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import svm.backend.sms.core.SmsMessage;
import svm.backend.sms.core.SmsSender;
import svm.backend.sms.core.SmsStatus;
import svm.backend.sms.mts.config.MtsProperties;
import svm.backend.sms.mts.model.ArrayOfDeliveryInfo;
import svm.backend.sms.mts.model.DeliveryInfo;
import svm.backend.sms.mts.model.SendMessageResponse;

@RequiredArgsConstructor
public class MtsSmsSender implements SmsSender {
    
    private final Logger logger = LoggerFactory.getLogger(MtsSmsSender.class);
    
    private final RestTemplateBuilder restTemplateBuilder;
    private final MtsProperties properties;
    
    private RestTemplate restTemplate;
        
    @PostConstruct
    public void initialize() {
        restTemplate = restTemplateBuilder.build();
    }
    
    @Override
    public String send(SmsMessage message) {
        
        String phoneNumber = normalizePhoneNumber(message.getPhoneNumber());
        
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("msid", phoneNumber);
        form.add("message", message.getText());
        form.add("naming", properties.getNaming());
        form.add("login", properties.getLogin());
        form.add("password", properties.getEncodedPassword());
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, new HttpHeaders());
        
        SendMessageResponse response;
        try {
            response = restTemplate.postForObject(properties.getSendUrl(), request, SendMessageResponse.class);
        } catch (HttpServerErrorException ex) {
            logger.error("Failed to send message to {}", phoneNumber, ex);
            throw ex;
        }

        long messageId = response.getId();
        logger.info("SMS to {} has been sent successfully with id {}", phoneNumber, messageId);
                
        return Long.toString(messageId);
        
    }
    
    private String normalizePhoneNumber(String source) {
        return source
                .replaceAll("\\+(\\d)", "7")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("-", "");
    }
        
    @Override
    public SmsStatus getMessageStatus(String id) {
                
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("messageId", id);
        form.add("login", properties.getLogin());
        form.add("password", properties.getEncodedPassword());
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, new HttpHeaders());
        
        ArrayOfDeliveryInfo statusList;
        try {
            statusList = restTemplate.postForObject(properties.getGetStatusUrl(), request, ArrayOfDeliveryInfo.class);
        } catch (HttpServerErrorException ex) {
            logger.error("Failed to get message status for {}. {}", id, ex.getResponseBodyAsString());
            throw ex;
        }
                        
        return parse(statusList);
        
    }
    
    private SmsStatus parse(ArrayOfDeliveryInfo statusList) {
        
        List<DeliveryInfo> list = statusList.getDeliveryInfoList();
        DeliveryInfo info = list.get(list.size() - 1);

        DeliveryInfo.Status deliveryStatus = info.getDeliveryStatus();
        SmsStatus.State state;
        
        switch(deliveryStatus) {
            case PENDING:
                state = SmsStatus.State.SENDING;
                break;
            case SENDING:
                state = SmsStatus.State.SENDING;
                break;
            case SENT:
                state = SmsStatus.State.SENDING;
                break;
            case DELIVERED:
                state = SmsStatus.State.DELIVERED;
                break;
            default:
                state = SmsStatus.State.ERROR;
                break;
        }

        ZonedDateTime createdAt = ZonedDateTime.ofInstant(info.getDeliveryDate().toInstant(), ZoneId.systemDefault());
        ZonedDateTime updatedAt = ZonedDateTime.ofInstant(info.getUserDeliveryDate().toInstant(), ZoneId.systemDefault());
                
        return SmsStatus.builder()
                .state(state)
                .updatedAt(createdAt.isAfter(updatedAt) ? createdAt : updatedAt)
                .build();
        
    }
                            
}
