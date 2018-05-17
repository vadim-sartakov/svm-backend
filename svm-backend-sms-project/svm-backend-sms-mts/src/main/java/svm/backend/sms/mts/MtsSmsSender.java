package svm.backend.sms.mts;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import svm.backend.sms.SmsMessage;
import svm.backend.sms.SmsSender;
import svm.backend.sms.mts.config.MtsProperties;
import svm.backend.sms.mts.model.SendMessageResponse;
import svm.backend.sms.mts.model.ArrayOfDeliveryInfo;
import svm.backend.sms.mts.model.DeliveryInfo;

public class MtsSmsSender implements SmsSender {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(MtsSmsSender.class);
    
    @Autowired private RestTemplateBuilder restTemplateBuilder;
    @Autowired private MtsProperties properties;
    
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
        } catch (RestClientException ex) {
            LOGGER.error("Failed to send message to {}", phoneNumber, ex);
            throw ex;
        }

        long messageId = response.getId();
        LOGGER.debug("SMS to {} has been sent successfully with id {}", phoneNumber, messageId);
        
        SmsMessage newMessage = message.toBuilder().externalId(Long.toString(messageId)).build();
        
        return newMessage;
        
    }
    
    private String normalizePhoneNumber(String source) {
        return source
                .replaceAll("\\+(\\d)", "7")
                .replaceAll("\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("-", "");
    }
        
    @Override
    public SmsMessage updateMessageStatus(SmsMessage smsMessage) {
        
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("messageId", smsMessage.getExternalId());
        form.add("login", properties.getLogin());
        form.add("password", properties.getEncodedPassword());
        
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, new HttpHeaders());
        
        ArrayOfDeliveryInfo statusList;
        
        try {
            statusList = restTemplate.postForObject(properties.getGetStatusUrl(), request, ArrayOfDeliveryInfo.class);
        } catch (HttpServerErrorException ex) {
            LOGGER.error("Failed to get message status for {}. {}", smsMessage.getExternalId(), ex.getResponseBodyAsString());
            throw ex;
        }
        
        List<DeliveryInfo> list = statusList.getDeliveryInfoList();
        DeliveryInfo info = list.get(list.size() - 1);

        SmsMessage.Status status = null;
        try {
            status = SmsMessage.Status.valueOf(info.getDeliveryStatus().toString());
        } catch (IllegalArgumentException e) { }
        ZonedDateTime updatedAt = ZonedDateTime.ofInstant(info.getDeliveryDate().toInstant(), ZoneId.systemDefault());
        
        SmsMessage newMessage = smsMessage.toBuilder()
                .status(status)
                .updatedAt(updatedAt)
                .build();
        
        return newMessage;
        
    }
                    
}
