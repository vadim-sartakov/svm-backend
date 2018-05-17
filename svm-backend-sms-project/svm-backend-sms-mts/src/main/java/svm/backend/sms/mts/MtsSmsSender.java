package svm.backend.sms.mts;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import svm.backend.sms.SmsMessage;
import svm.backend.sms.SmsSender;
import svm.backend.sms.mts.config.MtsProperties;
import svm.backend.sms.mts.model.SendMessageResponse;
import svm.backend.sms.mts.model.ArrayOfDeliveryInfo;
import svm.backend.sms.mts.model.DeliveryInfo;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
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
    public SmsMessage send(SmsMessage message) {
        
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
                
        return message.toBuilder()
                .id(Long.toString(messageId))
                .build();
        
    }
    
    private String normalizePhoneNumber(String source) {
        return source
                .replaceAll("\\+(\\d)", "7")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("-", "");
    }
        
    @Override
    public SmsMessage getMessageStatus(SmsMessage smsMessage) {
                
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("messageId", smsMessage.getId());
        form.add("login", properties.getLogin());
        form.add("password", properties.getEncodedPassword());
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, new HttpHeaders());
        
        ArrayOfDeliveryInfo statusList;
        try {
            statusList = restTemplate.postForObject(properties.getGetStatusUrl(), request, ArrayOfDeliveryInfo.class);
        } catch (HttpServerErrorException ex) {
            logger.error("Failed to get message status for {}. {}", smsMessage.getId(), ex.getResponseBodyAsString());
            throw ex;
        }
                        
        return convert(smsMessage, statusList);
        
    }
    
    private SmsMessage convert(SmsMessage smsMessage, ArrayOfDeliveryInfo statusList) {
        
        List<DeliveryInfo> list = statusList.getDeliveryInfoList();
        DeliveryInfo info = list.get(list.size() - 1);

        DeliveryInfo.Status deliveryStatus = info.getDeliveryStatus();
        SmsMessage.Status status;
        
        switch(deliveryStatus) {
            case PENDING:
                status = SmsMessage.Status.SENDING;
                break;
            case SENDING:
                status = SmsMessage.Status.SENDING;
                break;
            case SENT:
                status = SmsMessage.Status.SENDING;
                break;
            case DELIVERED:
                status = SmsMessage.Status.DELIVERED;
                break;
            default:
                status = SmsMessage.Status.ERROR;
                break;
        }

        ZonedDateTime createdAt = ZonedDateTime.ofInstant(info.getDeliveryDate().toInstant(), ZoneId.systemDefault());
        ZonedDateTime updatedAt = ZonedDateTime.ofInstant(info.getUserDeliveryDate().toInstant(), ZoneId.systemDefault());
                
        return smsMessage.toBuilder()
                .phoneNumber(info.getMsid())
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
        
    }
                            
}
